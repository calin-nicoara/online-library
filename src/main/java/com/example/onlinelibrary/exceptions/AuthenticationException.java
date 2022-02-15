package com.example.onlinelibrary.exceptions;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }
}
