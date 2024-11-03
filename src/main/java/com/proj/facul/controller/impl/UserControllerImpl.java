package com.proj.facul.controller.impl;

//import com.proj.facul.config.AppConfig;
import com.proj.facul.controller.UserController;
import com.proj.facul.domain.User;
import com.proj.facul.dto.request.UserRequest;
import com.proj.facul.dto.response.UserListResponse;
import com.proj.facul.dto.response.UserResponseDTO;
import com.proj.facul.repository.UserRepository;
import com.proj.facul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final UserRepository userRepository;
//    private final AppConfig appConfig;

    @Override
    public ResponseEntity<UserListResponse> getUsers() {
        final var users = userService.getUsers();
        final var responseList = new UserListResponse(users);
        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDTO> createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        User savedUser = userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
        return ResponseEntity.ok(userResponseDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> updateUser(Long id, UserRequest userRequest, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long id) {
        return null;
    }


}
