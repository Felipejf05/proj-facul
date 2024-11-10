package com.proj.facul.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    private String document;

    @Column
    @NotBlank
    private String birthday;

    @Column(unique = true)
    @NotNull
    private Long phone;

    @Column(unique = true)
    @NotBlank
    private String address;

    @Column(unique = true)
    @NotBlank
    private String email;

    @Column
    @NotBlank
    private String password;

    @OneToMany
    private List<Book> getBooks;

    public User(Long id, String name, String document, String birthday, Long phone, String address, String email, String password) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.password = password;
    }
}
