package com.lukkoc.users.domain.user.api;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserResponse {
    long id;
    String login;
    String name;
    String type;
    String avatarUrl;
    LocalDateTime createdAt;
    Double calculations;
}
