package com.proj.facul.service;

import com.proj.facul.domain.User;
import com.proj.facul.dto.request.UserUpdateRequest;
import com.proj.facul.exception.DuplicateAddressException;
import com.proj.facul.exception.DuplicateDocumentException;
import com.proj.facul.exception.DuplicateEmailException;
import com.proj.facul.exception.DuplicatePhoneException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
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

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenCreateUserWithAllArguments() {
        Long id = 1L;
        String name = "Mi";
        String document = "111134344545";
        String birthday = "30/05/1995";
        Long phone = 6767454535L;
        String address = "teste";
        String email = "mi@example.com";
        String password = "password123";

        User userToSave = new User(id, name, document, birthday, phone, address, email, password);
        when(userRepository.save(any(User.class))).thenReturn(userToSave);

        User createdUser = userService.createUser(userToSave);

        assertNotNull(createdUser);
        assertEquals(userToSave.getId(), createdUser.getId());
        assertEquals(userToSave.getName(), createdUser.getName());
        assertEquals(userToSave.getPhone(), createdUser.getPhone());
        assertEquals(userToSave.getAddress(), createdUser.getAddress());
        assertEquals(userToSave.getEmail(), createdUser.getEmail());
        assertEquals(userToSave.getPassword(), createdUser.getPassword());
        assertEquals(userToSave.getBirthday(), createdUser.getBirthday());

        verify(userRepository, times(1)).save(userToSave);
    }

    @Test
    public void whenGetUsersSuccess() {
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "Jorge", "444.xxxxx", "30/05/1995", 999999L, "test", "jorge@example.com", "password123"),
                new User(2L, "Felipe", "111.xxxxx", "30/05/1998", 8888888L, "test", "jfelipe@example.com", "password456")
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
        User user = new User(1L, "test", "444.xxxxx", "30/05/1995", 119999999L, "Rua teste", "jorge@gmail.com", "password123");
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
        String birthdayOriginal = "30/05/1994";
        String birthdayUpdated = "30/05/1995";

        // Usuário original do banco de dados
        User userToUpdate = new User(userId, "Mirian", "444.xxxxx", birthdayOriginal, 99999999L, "teste", "miriam@gmail.com", "12345");

        // Solicitação de atualização
        UserUpdateRequest updatedUserRequest = new UserUpdateRequest("Jorge Felipe", birthdayUpdated, "1198777453", "rua armando", "jorge@example.com", "565657");

        // Usuário atualizado esperado
        User updatedUser = new User(userId, "Jorge Felipe", "333.xxxxx", birthdayUpdated, 118888888L, "Rua cruzeiro", "felipee@example.com", "45565667");


        when(userRepository.findById(userId)).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, updatedUserRequest);

        assertNotNull(result);
        assertEquals("Jorge Felipe", result.getName());
        assertEquals("333.xxxxx", result.getDocument());
        assertEquals(118888888L, result.getPhone());
        assertEquals("Rua cruzeiro", result.getAddress());
        assertEquals("felipee@example.com", result.getEmail());
        assertEquals("45565667", result.getPassword());


        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldThrowDuplicateDocumentExceptionWhenCreatingNewUserWithExistingDocument() {

        User user = new User();
        user.setDocument("12345678901");

        when(userRepository.existsByDocument(user.getDocument())).thenReturn(true);

        assertThrows(DuplicateDocumentException.class, () -> {
            userService.validateDuplicates(user, null);
        });
    }

    @Test
    public void shouldThrowDuplicateEmailExceptionWhenCreatingNewUserWithExistingEmail() {

        User user = new User();
        user.setEmail("felipe@gmail.com");

        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> {
            userService.validateDuplicates(user, null);
        });
    }

    @Test
    public void shouldThrowDuplicatePhoneExceptionWhenCreatingNewUserWithExistingPhone() {

        User user = new User();
        user.setPhone(98888888L);

        when(userRepository.existsByPhone(user.getPhone())).thenReturn(true);

        assertThrows(DuplicatePhoneException.class, () -> {
            userService.validateDuplicates(user, null);
        });
    }

    @Test
    public void shouldThrowDuplicateAddressExceptionWhenCreatingNewUserWithExistingAddress() {

        User user = new User();
        user.setAddress("Rua Av Armando");

        when(userRepository.existsByAddress(user.getAddress())).thenReturn(true);

        assertThrows(DuplicateAddressException.class, () -> {
            userService.validateDuplicates(user, null);
        });
    }

    @Test
    public void shouldThrowDuplicatePhoneExceptionWhenUpdatingUserWithExistingPhone() {

        Long userId = 1L;
        User existingUser = new User(userId, "João", "12345678901", "10/11/2024", 9887776777L, "rua teste", "felip@gmail.com", "45465455");
        UserUpdateRequest request = new UserUpdateRequest();
        request.setPhone("123456789");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByPhoneAndIdNot(Long.valueOf(request.getPhone()), userId)).thenReturn(true);

        assertThrows(DuplicatePhoneException.class, () -> {
            userService.updateUser(userId, request);
        });
    }

    @Test
    public void shouldThrowDuplicateEmailExceptionWhenUpdatingUserWithExistingEmail() {

        Long userId = 1L;
        User existingUser = new User(userId, "João", "12345678901", "10/11/2024", 9887776777L, "rua teste", "felip@gmail.com", "45465455");
        UserUpdateRequest request = new UserUpdateRequest();
        request.setEmail("felipe@gmail.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmailAndIdNot(request.getEmail(), userId)).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> {
            userService.updateUser(userId, request);
        });
    }

    @Test
    public void shouldThrowDuplicateAddressExceptionWhenUpdatingUserWithExistingAddress() {

        Long userId = 1L;
        User existingUser = new User(userId, "João", "12345678901", "10/11/2024", 9887776777L, "rua teste", "felip@gmail.com", "45465455");
        UserUpdateRequest request = new UserUpdateRequest();
        request.setAddress("Rua armando");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByAddressAndIdNot(request.getAddress(), userId)).thenReturn(true);

        assertThrows(DuplicateAddressException.class, () -> {
            userService.updateUser(userId, request);
        });
    }

    @Test
    public void whenUpdateUserUserNotFound() {
        // Cria a solicitação de atualização (UserUpdateRequest)
        UserUpdateRequest updatedUserRequest = new UserUpdateRequest(
                "Felipe", "30/05/1195", "1198888888", "Av armando", "felipe@gmail.com", "4545453543"
        );

        // Mock para o repositório retornando Optional.empty(), indicando que o usuário não foi encontrado
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Espera-se uma exceção RuntimeException com a mensagem "Usuário não encontrado"
        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUser(1L, updatedUserRequest));
        assertEquals("Usuário não encontrado", exception.getMessage());


        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(0)).save(any(User.class));
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