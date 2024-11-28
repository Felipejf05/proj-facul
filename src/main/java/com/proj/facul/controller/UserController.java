package com.proj.facul.controller;

import com.proj.facul.dto.request.LoginRequest;
import com.proj.facul.dto.request.UserRequest;
import com.proj.facul.dto.request.UserUpdateRequest;
import com.proj.facul.dto.response.UserListResponse;
import com.proj.facul.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "User")
@RequestMapping("/v1/users")
public interface UserController {

    @GetMapping("/list-users")
    @Operation(summary = "Trás a lista de usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários gerada com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserListResponse.class)))
    ResponseEntity<UserListResponse> getUsers();

    @GetMapping("/users/{id}")
    @Operation(summary = "Busca um usuário por ID")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<UserResponseDTO> findById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequest userRequest);

    @PutMapping("/users/{id}")
    @Operation(summary = "Atualiza um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest);

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Deleta um usuário")
    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ResponseEntity<Void> deleteUser(@PathVariable Long id);

    @PostMapping("/login")
    @Operation(summary = "Realiza o login do usuário")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
            content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário não encontrado")
    ResponseEntity<UserResponseDTO> login(@RequestBody @Valid LoginRequest loginRequest);
}
