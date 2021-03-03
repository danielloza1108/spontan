package com.example.spontan.exception;

public class EventNotExist extends RuntimeException{
    public EventNotExist(String message){
        super(message);
    }
}
