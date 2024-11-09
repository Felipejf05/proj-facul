package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService
            userService;

    private User user;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenCreateUserWithAllArguments() {

        Long id = 1L;
        String name = "Mi";
        String phone = "6767454535";
        String address = "teste";
        String email = "mi@example.com";
        String password = "password123";

        User userToSave = new User(id, name, phone, address, email, password);

        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        User createdUser = userService.createUser(userToSave);

        assertNotNull(createdUser);
        assertEquals(userToSave.getId(), createdUser.getId());
        assertEquals(userToSave.getName(), createdUser.getName());
        assertEquals(userToSave.getPhone(), createdUser.getPhone());
        assertEquals(userToSave.getAddress(), createdUser.getAddress());
        assertEquals(userToSave.getEmail(), createdUser.getEmail());
        assertEquals(userToSave.getPassword(), createdUser.getPassword());


        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    public void whenGetUsersSuccess() {

        List<User> expectedUsers = Arrays.asList(
                new User(1L, "Jorge", "999999", "test", "jorge@example.com", "password123"),
                new User(2L, "Felipe", "8888888", "test", "jfelipe@example.com", "password456")
        );

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).findAll();

    }

    @Test
    public void whenGetUsersReturnFails() {

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> actualUsers = userService.getUsers();

        assertEquals(Collections.emptyList(), actualUsers);

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void whenGetUserByIdSuccess() {
        User user = new User(1L, "test", "119999999", "Rua teste", "jorge@gmail.com", "password123");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void whenGetUserByIdUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void whenUpdateUserSuccessfully() {
        Long userId = 1L;
        User userToUpdate = new User(userId, "Mirian", "99999999", "teste", "miriam@gmail.com", "12345");
        User updatedUser = new User(userId, "Jorge Felipe", "118888888", "Rua cruzeiro", "jorge@example.com", "45565667");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUser);

        assertNotNull(result);
        assertEquals("Jorge Felipe", result.getName());
        assertEquals("118888888", result.getPhone());
        assertEquals("Rua cruzeiro", result.getAddress());
        assertEquals("jorge@example.com", result.getEmail());
        assertEquals("45565667", result.getPassword());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void whenUpdateUserUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, user));
        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void whenDeleteUserSuccess() {
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void whenDeleteUserIdNotFound() {
        doThrow(new RuntimeException("Id não encontrado"))
                .when(userRepository)
                .deleteById(1L);

        Exception exception = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(1L));

        assertEquals("Id não encontrado", exception.getMessage());
        verify(userRepository, times(1)).deleteById(1L);
    }
}