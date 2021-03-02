package com.example.spontan.exception;

public class UserAlreadyInDBException extends RuntimeException {

    public UserAlreadyInDBException(String message) {
        super(message);
    }
}
