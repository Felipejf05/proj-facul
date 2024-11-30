package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.dto.request.UserUpdateRequest;
import com.proj.facul.exception.DuplicateDocumentException;
import com.proj.facul.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setDocument("123456789");
        user.setPhone(1234567890L);
        user.setAddress("123 Main St");
        user.setPassword("password123");
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.existsByDocument(user.getDocument())).thenReturn(false);
        when(userRepository.existsByPhone(user.getPhone())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.existsByAddress(user.getAddress())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getName(), createdUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_DocumentAlreadyExists() {
        when(userRepository.existsByDocument(user.getDocument())).thenReturn(true);

        DuplicateDocumentException exception = assertThrows(DuplicateDocumentException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("O documento fornecido já está cadastrado.", exception.getMessage());
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User fetchedUser = userService.getUserById(user.getId());

        assertNotNull(fetchedUser);
        assertEquals(user.getId(), fetchedUser.getId());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(user.getId());
        });
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void testUpdateUser_Success() {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setName("John Updated");
        updateRequest.setEmail("johnupdated@example.com");
        updateRequest.setPhone("987654321");
        updateRequest.setAddress("456 Another St");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsByDocumentAndIdNot(user.getDocument(), user.getId())).thenReturn(false);
        when(userRepository.existsByPhoneAndIdNot(user.getPhone(), user.getId())).thenReturn(false);
        when(userRepository.existsByEmailAndIdNot(user.getEmail(), user.getId())).thenReturn(false);
        when(userRepository.existsByAddressAndIdNot(user.getAddress(), user.getId())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user.getId(), updateRequest);

        assertNotNull(updatedUser);
        assertEquals("John Updated", updatedUser.getName());
        assertEquals("johnupdated@example.com", updatedUser.getEmail());
        assertEquals(987654321L, updatedUser.getPhone());
        assertEquals("456 Another St", updatedUser.getAddress());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void testFindByDocument_Success() {
        when(userRepository.findByDocument(user.getDocument())).thenReturn(Optional.of(user));
        User fetchedUser = userService.findByDocument(user.getDocument());

        assertNotNull(fetchedUser);
        assertEquals(user.getDocument(), fetchedUser.getDocument());
    }

    @Test
    void testFindByDocument_NotFound() {

        when(userRepository.findByDocument(user.getDocument())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByDocument(user.getDocument());
        });
        assertEquals("Usuário não encontrado com o documento fornecido", exception.getMessage());
    }

    @Test
    void testGetUserPassword_Success() {
        when(userRepository.findByDocument(user.getDocument())).thenReturn(Optional.of(user));

        String password = userService.getUserPassword(user.getDocument());

        assertEquals(user.getPassword(), password);
    }

    @Test
    void testGetUserPassword_NotFound() {
        when(userRepository.findByDocument(user.getDocument())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserPassword(user.getDocument());
        });
        assertEquals("Usuário não encontrado com o documento fornecido", exception.getMessage());
    }
}
