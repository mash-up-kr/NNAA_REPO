package com.na.backend.exception;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errMessage;

    public UnauthorizedException(String errMessage) {
        this.errMessage = errMessage;
    }
}
