package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidIdTokenException extends BaseException {

    public InvalidIdTokenException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidIdTokenException ]\n" + message)
                .build());
    }
}

