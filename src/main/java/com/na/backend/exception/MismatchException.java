package com.na.backend.exception;

import org.springframework.http.HttpStatus;

public class MismatchException extends BaseException  {

    public MismatchException(String message) {
        super(ApiError.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("[ MismatchException ]\n" + message)
                .build());
    }
}
