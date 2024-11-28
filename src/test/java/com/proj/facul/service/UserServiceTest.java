package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void whenFindByDocumentSuccess() {
        String document = "12345678901";
        User expectedUser = new User(1L, "João", document, "10/11/1995", 123456789L, "Rua Teste", "joao@example.com", "password123");

        when(userRepository.findByDocument(document)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.findByDocument(document);

        assertNotNull(foundUser);
        assertEquals(expectedUser.getId(), foundUser.getId());
        assertEquals(expectedUser.getDocument(), foundUser.getDocument());
        verify(userRepository, times(1)).findByDocument(document);
    }

    @Test
    public void whenFindByDocumentThrowsException() {
        String document = "12345678901";

        when(userRepository.findByDocument(document)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findByDocument(document));
        assertEquals("Usuário não encontrado com o documento fornecido", exception.getMessage());
        verify(userRepository, times(1)).findByDocument(document);
    }

    @Test
    public void whenFindByEmailSuccess() {
        String email = "joao@example.com";
        User expectedUser = new User(1L, "João", "12345678901", "10/11/1995", 123456789L, "Rua Teste", email, "password123");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(expectedUser.getId(), foundUser.getId());
        assertEquals(expectedUser.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void whenFindByEmailThrowsException() {
        String email = "joao@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findByEmail(email));
        assertEquals("Usuário não encontrado com o e-mail fornecido", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    public void whenGetUserPasswordSuccess() {
        String document = "12345678901";
        String password = "password123";
        User expectedUser = new User(1L, "João", document, "10/11/1995", 123456789L, "Rua Teste", "joao@example.com", password);

        when(userRepository.findByDocument(document)).thenReturn(Optional.of(expectedUser));

        String retrievedPassword = userService.getUserPassword(document);

        assertNotNull(retrievedPassword);
        assertEquals(password, retrievedPassword);
        verify(userRepository, times(1)).findByDocument(document);
    }

    @Test
    public void whenGetUserPasswordThrowsException() {
        String document = "12345678901";

        when(userRepository.findByDocument(document)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.getUserPassword(document));
        assertEquals("Usuário não encontrado com o documento fornecido", exception.getMessage());
        verify(userRepository, times(1)).findByDocument(document);
    }
}
