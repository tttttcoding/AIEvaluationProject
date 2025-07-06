package com.example.aiteach.exception;

public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException(String message){
        super(message);
    }
}
