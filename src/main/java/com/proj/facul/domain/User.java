package com.proj.facul.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column
    @NotBlank
    private String email;

    @Column
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Book> getBooks;
}
