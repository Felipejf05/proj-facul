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
        validateDuplicates(user, null);  // Verifica duplicidade antes de criar
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User existingUser = getUserById(id);  // Busca o usuário pelo ID

        // Atualiza os campos com base nas informações fornecidas
        if (userUpdateRequest.getName() != null) {
            existingUser.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getBirthday() != null) {
            existingUser.setBirthday(userUpdateRequest.getBirthday());
        }
        if (userUpdateRequest.getPhone() != null) {
            existingUser.setPhone(Long.valueOf(userUpdateRequest.getPhone()));  // Atualiza o telefone
        }
        if (userUpdateRequest.getAddress() != null) {
            existingUser.setAddress(userUpdateRequest.getAddress());  // Atualiza o endereço
        }
        if (userUpdateRequest.getEmail() != null) {
            existingUser.setEmail(userUpdateRequest.getEmail());  // Atualiza o email
        }
        if (userUpdateRequest.getPassword() != null) {
            existingUser.setPassword(userUpdateRequest.getPassword());  // Atualiza a senha
        }

        // Verifica se há duplicidade antes de salvar as atualizações
        validateDuplicates(existingUser, id);

        return userRepository.save(existingUser);  // Salva o usuário atualizado
    }

    void validateDuplicates(User user, Long existingUserId) {
        // Validação de duplicidade para documentos, telefones, e-mails e endereços
        if (existingUserId == null) {  // Se for um novo usuário
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
        } else {  // Se for uma atualização, ignoramos o usuário atual
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
            userRepository.deleteById(id);  // Deleta o usuário
        } catch (RuntimeException e) {
            throw new RuntimeException("Id não encontrado");  // Lança exceção caso o usuário não seja encontrado
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();  // Retorna a lista de todos os usuários
    }
}
