package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.dto.request.UserUpdateRequest;
import com.proj.facul.exception.DuplicateAddressException;
import com.proj.facul.exception.DuplicateDocumentException;
import com.proj.facul.exception.DuplicateEmailException;
import com.proj.facul.exception.DuplicatePhoneException;
import com.proj.facul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        validateDuplicates(user, null);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User existingUser = getUserById(id); // Busca o usuário existente

        // Atualiza os campos do usuário
        if (userUpdateRequest.getName() != null) {
            existingUser.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getBirthday() != null) {
            existingUser.setBirthday(userUpdateRequest.getBirthday());
        }
        if (userUpdateRequest.getPhone() != null) {
            existingUser.setPhone(Long.valueOf(userUpdateRequest.getPhone()));
        }
        if (userUpdateRequest.getAddress() != null) {
            existingUser.setAddress(userUpdateRequest.getAddress());
        }
        if (userUpdateRequest.getEmail() != null) {
            existingUser.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getPassword() != null) {
            existingUser.setPassword(userUpdateRequest.getPassword());
        }

        validateDuplicates(existingUser, id);

        return userRepository.save(existingUser);
    }

    void validateDuplicates(User user, Long existingUserId) {

        // Se for um novo usuário, validamos a duplicidade
        if (existingUserId == null) {
            if (userRepository.existsByDocument(user.getDocument())) {
                throw new DuplicateDocumentException("O documento fornecido já está cadastrado.");
            }
            if (userRepository.existsByPhone(user.getPhone())) {
                throw new DuplicatePhoneException("O telefone fornecido já está cadastrado.");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new DuplicateEmailException("O email fornecido já está cadastrado.");
            }
            if (userRepository.existsByAddress(user.getAddress())) {
                throw new DuplicateAddressException("O endereço informado já está cadastrado.");
            }
        } else {
            // Se estamos atualizando, ignoramos o usuário atual na validação
            if (userRepository.existsByDocumentAndIdNot(user.getDocument(), existingUserId)) {
                throw new DuplicateDocumentException("O documento fornecido já está cadastrado.");
            }
            if (userRepository.existsByPhoneAndIdNot(user.getPhone(), existingUserId)) {
                throw new DuplicatePhoneException("O telefone fornecido já está cadastrado.");
            }
            if (userRepository.existsByEmailAndIdNot(user.getEmail(), existingUserId)) {
                throw new DuplicateEmailException("O email fornecido já está cadastrado.");
            }
            if (userRepository.existsByAddressAndIdNot(user.getAddress(), existingUserId)) {
                throw new DuplicateAddressException("O endereço informado já está cadastrado.");
            }
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Id não encontrado");
        }

    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
