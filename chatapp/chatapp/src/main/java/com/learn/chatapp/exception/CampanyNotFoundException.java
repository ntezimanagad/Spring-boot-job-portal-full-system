package com.learn.chatapp.exception;

public class CampanyNotFoundException extends RuntimeException {
    public CampanyNotFoundException(String message) {
        super(message);
    }
}
