package com.example.aiteach.exception;

public class TokenUnauthorized extends RuntimeException{
    public TokenUnauthorized(String message){
        super(message);
    }
}
