package com.example.aiteach.exception;

public class ToolAlreadyExistsException extends RuntimeException{
    public ToolAlreadyExistsException(String message){
        super(message);
    }
}
