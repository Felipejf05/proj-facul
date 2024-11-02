package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.repository.UseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UseService {

    private final UseRepository useRepository;


    public User createUser(User user){
        return useRepository.save(user);
    }

    public User getUserById(Long id){
        Optional<User> user = useRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User updateUser(Long id, User user){
        Optional<User> existingUser = useRepository.findById(id);
        if(existingUser.isPresent()){
            existingUser.ifPresent(value -> value.setName(user.getName()));
            existingUser.ifPresent(value -> value.setEmail(user.getEmail()));
            existingUser.ifPresent(value -> value.setPassword(user.getPassword()));
            return useRepository.save(existingUser.get());
        }else{
            throw  new RuntimeException("Usuário não encontrado");
        }
    }

    public void deleteUser(Long id){
        try{
            useRepository.deleteById(id);
        }catch (RuntimeException e){
            throw new RuntimeException("Id não encotrado");
        }

    }
    public List<User> getUsers(){
        return useRepository.findAll();
    }


}
