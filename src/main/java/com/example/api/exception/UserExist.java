package com.example.api.exception;

public class UserExist extends RuntimeException{
    public UserExist() {
        super();
    }

    public UserExist(String message) {
        super(message);
    }
}
