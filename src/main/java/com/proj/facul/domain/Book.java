package com.proj.facul.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String title;

    @Column
    @NotBlank
    private String author;

    @Column(name = "publication_year")
    @NotBlank
    private Date publication_year;

    @NotBlank
    private String genre;

    @NotBlank
    private String description;

    @NotBlank
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
