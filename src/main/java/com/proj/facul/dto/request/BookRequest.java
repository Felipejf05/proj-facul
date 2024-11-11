package com.proj.facul.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "O título não pode estar em branco.")
    private String title;

    @NotBlank(message = "O nome do autor não deve estar em branco.")
    private String author;

    @NotNull(message = "A data de publicação do livro não deve estar em branco.")
    private String publicationYear;

    @NotBlank(message = "O genêro não pode ficar em branco.")
    private String genre;

    @NotBlank(message = "A descrição não pode ficar em branco")
    private String description;

    @NotNull(message = "A disponibilidade não pode ficar em branco.")
    private Boolean available;
}
