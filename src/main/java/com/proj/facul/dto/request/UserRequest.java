package com.proj.facul.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String name;
    private String email;
    private String password;
}
