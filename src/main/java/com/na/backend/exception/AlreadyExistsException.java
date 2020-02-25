package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends BaseException {

    public AlreadyExistsException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ AlreadyExistsException ]\n" + message)
                .build());
    }
}
