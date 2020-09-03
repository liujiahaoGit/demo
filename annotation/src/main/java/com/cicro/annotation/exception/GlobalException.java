package com.cicro.annotation.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(IdempotenceException.class)
    public String e(IdempotenceException e) {
        return e.getMessage();
    }

}
