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

    @Column
    @NotBlank
    private String genre;

    @Column
    @NotBlank
    private String description;

    @Column
    @NotBlank
    private Boolean available;

    @ManyToOne
    @JoinColumn(name = "app_user")
    private User user;
}
