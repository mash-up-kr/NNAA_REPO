package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidStringException extends BaseException {

    public InvalidStringException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidStringException ]\n" + message)
                .build());
    }
}
