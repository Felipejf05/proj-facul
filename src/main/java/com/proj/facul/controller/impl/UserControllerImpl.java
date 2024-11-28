package com.proj.facul.controller.impl;

import com.proj.facul.controller.UserController;
import com.proj.facul.domain.User;
import com.proj.facul.dto.request.LoginRequest;
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
        responseDTO.setId(String.valueOf(user.getId()));
        responseDTO.setName(user.getName());
        responseDTO.setDocument(user.getDocument());
        responseDTO.setBirthday(user.getBirthday());
        responseDTO.setPhone(user.getPhone().toString());
        responseDTO.setAddress(user.getAddress());
        responseDTO.setEmail(user.getEmail());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setDocument(userRequest.getDocument());
        user.setBirthday(userRequest.getBirthday());
        user.setPhone(Long.valueOf(userRequest.getPhone()));
        user.setAddress(userRequest.getAddress());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User savedUser = userService.createUser(user);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(String.valueOf(savedUser.getId()));
        responseDTO.setName(savedUser.getName());
        responseDTO.setDocument(savedUser.getDocument());
        responseDTO.setBirthday(savedUser.getBirthday());
        responseDTO.setPhone(savedUser.getPhone().toString());
        responseDTO.setAddress(savedUser.getAddress());
        responseDTO.setEmail(savedUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        User updatedUser = userService.updateUser(id, userUpdateRequest);

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(String.valueOf(updatedUser.getId()));
        responseDTO.setName(updatedUser.getName());
        responseDTO.setDocument(updatedUser.getDocument());
        responseDTO.setPhone(updatedUser.getPhone().toString());
        responseDTO.setBirthday(updatedUser.getBirthday());
        responseDTO.setEmail(updatedUser.getEmail());
        responseDTO.setAddress(updatedUser.getAddress());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<UserResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = null;

        try {
            if (loginRequest.getUser().matches("[0-9]{11}")) {
                user = userService.findByDocument(loginRequest.getUser());
            } else {
                user = userService.findByEmail(loginRequest.getUser());
            }

            if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
                UserResponseDTO responseDTO = new UserResponseDTO();
                responseDTO.setId(String.valueOf(user.getId()));
                responseDTO.setName(user.getName());
                responseDTO.setDocument(user.getDocument());
                responseDTO.setBirthday(user.getBirthday());
                responseDTO.setPhone(user.getPhone().toString());
                responseDTO.setAddress(user.getAddress());
                responseDTO.setEmail(user.getEmail());

                return ResponseEntity.ok(responseDTO);
            } else {
                throw new RuntimeException("Credenciais inválidas");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
