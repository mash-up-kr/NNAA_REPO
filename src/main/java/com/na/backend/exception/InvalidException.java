package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidException extends BaseException {

    public InvalidException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidException ]\n" + message)
                .build());
    }
}
