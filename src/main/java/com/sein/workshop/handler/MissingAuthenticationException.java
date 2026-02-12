package com.sein.workshop.handler;

public class MissingAuthenticationException extends RuntimeException {
    public MissingAuthenticationException(String message) {
        super(message);
    }
}
