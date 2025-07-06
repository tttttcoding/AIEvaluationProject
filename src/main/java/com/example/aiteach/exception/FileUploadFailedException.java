package com.example.aiteach.exception;

public class FileUploadFailedException extends RuntimeException{
    public FileUploadFailedException(String message){
        super(message);
    }
}
