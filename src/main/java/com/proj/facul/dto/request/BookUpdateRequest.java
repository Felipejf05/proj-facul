package com.proj.facul.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class BookUpdateRequest {
    private String title;
    private String author;
    private Date publicationYear;
    private String genre;
    private String description;
    private Boolean available;
}
