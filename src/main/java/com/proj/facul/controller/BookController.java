package com.proj.facul.controller;

import com.proj.facul.dto.request.BookRequest;
import com.proj.facul.dto.request.BookUpdateRequest;
import com.proj.facul.dto.response.BookListResponse;
import com.proj.facul.dto.response.BookResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;

@Tag(name = "Book")
@RequestMapping("/v1")
public interface BookController {

    @GetMapping("/list-books")
    @Operation(summary = "Lista todos os livros")
    @ApiResponse(responseCode = "200",
            description = "Lista de livros gerada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookListResponse.class)))
    ResponseEntity<BookListResponse> getBooks();

    @GetMapping("/books/{id}")
    @Operation(summary = "Busca um livro por ID")
    @ApiResponse(responseCode = "200", description = "Livro encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    ResponseEntity<BookResponseDTO> findById(@PathVariable Long id);

    @PostMapping("/books")
    @Operation(summary = "Adiciona um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<BookResponseDTO> addBook(@Valid @RequestBody BookRequest bookRequest) throws ParseException;

    @PutMapping("/books/{id}")
    @Operation(summary = "Atualiza as informações de um livro")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @ApiResponse(responseCode = "400", description = "Erro de validação")
    ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest bookUpdateRequest) throws ParseException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um livro")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    ResponseEntity<Void> deleteBook(@PathVariable Long id);

    @PostMapping("/books/{id}/upload")
    @Operation(
            summary = "Faz o upload de um arquivo associado ao livro",
            description = "Endpoint que permite o upload de um arquivo para um livro específico.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID do livro para o qual o arquivo está sendo enviado",
                            required = true,
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "integer", format = "int64")
                    )
            },
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(type = "string", format = "binary")
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Upload realizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao fazer upload do arquivo")
    ResponseEntity<String> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file);

    @GetMapping("/books/{id}/download")
    @Operation(summary = "Baixa o arquivo associado ao livro")
    @ApiResponse(responseCode = "200", description = "Arquivo baixado com sucesso")
    ResponseEntity<byte[]> downloadFile(@PathVariable Long id);

    @DeleteMapping("/books/{id}/delete-file")
    @Operation(summary = "Deleta o arquivo associado ao livro")
    @ApiResponse(responseCode = "204", description = "Arquivo deletado com sucesso")
    ResponseEntity<Void> deleteFile(@PathVariable Long id);
}