package com.example.spontan.exception;

public class EventNotExistException extends RuntimeException{
    public EventNotExistException(String message){
        super(message);
    }
}
