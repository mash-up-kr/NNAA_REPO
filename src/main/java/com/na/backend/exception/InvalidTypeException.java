package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidTypeException extends BaseException {

    public InvalidTypeException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidTypeException ]\n" + message)
                .build());
    }
}
