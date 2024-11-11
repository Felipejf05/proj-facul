package com.proj.facul.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {
    private String id;
    private String title;
    private String author;
    private String publicationYear;
    private String genre;
    private String description;
    private Boolean available;
}
