package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidProviderException extends BaseException {

    public InvalidProviderException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidProviderException ]\n" + message)
                .build());
    }
}
