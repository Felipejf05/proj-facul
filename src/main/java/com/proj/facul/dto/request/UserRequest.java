package com.proj.facul.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "O nome não pode estar em branco.")
    private String name;

    @NotBlank(message = "O documento não pode estar em branco.")
    private String document;

    @NotBlank(message = "A data de aniversário não pode estar em branco.")
    private String birthday;

    @NotNull(message = "O telefone não pode estar em branco.")
    private String phone;

    @NotBlank(message = "O endereço não pode estar em branco.")
    private String address;

    @NotBlank(message = "O email não pode estar em branco.")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String password;

}