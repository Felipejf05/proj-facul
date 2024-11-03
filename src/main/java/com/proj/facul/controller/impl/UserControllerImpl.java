package com.proj.facul.controller.impl;

import com.proj.facul.controller.UserController;
import com.proj.facul.domain.User;
import com.proj.facul.dto.request.UserRequest;
import com.proj.facul.dto.request.UserUpdateRequest;
import com.proj.facul.dto.response.UserListResponse;
import com.proj.facul.dto.response.UserResponseDTO;
import com.proj.facul.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<UserListResponse> getUsers() {
        final var users = userService.getUsers();
        final var responseList = new UserListResponse(users);
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findById(Long id) {

        User user = userService.getUserById(id);
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setEmail(user.getEmail());

        return ResponseEntity.ok(responseDTO);

    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User savedUser = userService.createUser(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setPhone(savedUser.getPhone());
        responseDTO.setAddress(savedUser.getAddress());
        responseDTO.setEmail(savedUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {

        User existingUser = userService.getUserById(id);

        if (userUpdateRequest.getName() != null) {
            existingUser.setName(userUpdateRequest.getName());
        }
        if(userUpdateRequest.getPhone() != null){
            existingUser.setPhone(userUpdateRequest.getPhone());
        }
        if(userUpdateRequest.getAddress() != null){
            existingUser.setAddress(userUpdateRequest.getAddress());
        }
        if (userUpdateRequest.getEmail() != null) {
            existingUser.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getPassword() != null) {
            existingUser.setPassword(userUpdateRequest.getPassword());
        }
        if (userUpdateRequest.getAddress() != null) {
            existingUser.setAddress(userUpdateRequest.getAddress());
        }

        User updatedUser = userService.updateUser(id, existingUser);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(updatedUser.getId());
        responseDTO.setName(updatedUser.getName());
        responseDTO.setEmail(updatedUser.getEmail());
        responseDTO.setAddress(updatedUser.getAddress());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try{
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}