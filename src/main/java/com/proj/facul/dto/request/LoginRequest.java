package com.proj.facul.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "O CPF ou e-mail não pode estar em branco.")
    private String user;

    @NotBlank(message = "A senha não pode estar em branco.")
    private String password;
}
