package com.cicro.annotation.exception;

public class IdempotenceException extends RuntimeException{

    public IdempotenceException(String message) {
        super(message);
    }
}
