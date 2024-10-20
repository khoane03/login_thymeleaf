package com.it.exception;

public class AppException extends RuntimeException {

    public AppException(ErrorMessage error) {
        super(error.getMessage());
    }
}
