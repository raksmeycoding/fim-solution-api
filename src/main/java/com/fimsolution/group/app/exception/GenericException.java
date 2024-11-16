package com.fimsolution.group.app.exception;

public class GenericException extends RuntimeException {

    String messageCode;
    String message;

    public GenericException(String message) {
        super(message);
    }
}
