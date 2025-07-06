package com.example.aiteach.exception;

public class ToolNotFoundException extends RuntimeException{
    public ToolNotFoundException(String message){
        super(message);
    }
}
