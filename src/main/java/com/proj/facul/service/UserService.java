package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, User user){
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()){
            existingUser.ifPresent(value -> value.setName(user.getName()));
            existingUser.ifPresent(value -> value.setEmail(user.getEmail()));
            existingUser.ifPresent(value -> value.setPassword(user.getPassword()));
            return userRepository.save(existingUser.get());
        }else{
            throw  new RuntimeException("Usuário não encontrado");
        }
    }

    public void deleteUser(Long id){
        try{
            userRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("Id não encontrado");
        }

    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

}
