package com.na.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<String> restExceptionHandler(HttpServletRequest req, BaseException exception)
            throws RuntimeException {
        ApiError apiError = exception.getApiError();

        return ResponseEntity
                .status(apiError.getHttpStatus())
                .body(apiError.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Exception> unhandledExceptionHandler(RuntimeException exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception);
    }
}