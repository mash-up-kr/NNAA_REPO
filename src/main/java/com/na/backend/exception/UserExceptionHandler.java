package com.na.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(value = HttpStatus.OK, reason = "Invalid Provider")
    @ExceptionHandler(InvalidProviderException.class)
    public void invalidProvider() {
        // 로그 찍기
    }

    @ResponseStatus(value = HttpStatus.OK, reason = "Invalid Id token.")
    @ExceptionHandler(InvalidIdTokenException.class)
    public void invalidIdToken() {

    }

}

