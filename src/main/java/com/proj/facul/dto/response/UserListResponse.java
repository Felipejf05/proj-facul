package com.proj.facul.dto.response;

import com.proj.facul.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserListResponse {
    private List<User> data;
}
