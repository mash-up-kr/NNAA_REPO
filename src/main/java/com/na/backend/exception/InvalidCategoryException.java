package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidCategoryException extends BaseException {

    public InvalidCategoryException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ InvalidCategoryException ]\n" + message)
                .build());
    }
}
