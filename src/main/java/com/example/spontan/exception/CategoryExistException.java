package com.example.spontan.exception;

public class CategoryExistException extends RuntimeException {
    public CategoryExistException(String message) {
        super(message);
    }
}
