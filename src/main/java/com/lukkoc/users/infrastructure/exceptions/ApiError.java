package com.lukkoc.users.infrastructure.exceptions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class ApiError {
    Integer status;
    String message;

    ApiError(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
    }
}