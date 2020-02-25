package com.na.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ EntityNotFoundException ]\n" + message)
                .build());
    }
}