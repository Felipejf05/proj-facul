package com.proj.facul.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String phone;
    private String address;
    private String email;
    private String password;
}
