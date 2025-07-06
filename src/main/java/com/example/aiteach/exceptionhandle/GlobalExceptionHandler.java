package com.example.aiteach.exceptionhandle;

import com.example.aiteach.DTO.MessageDTO;
import com.example.aiteach.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
    //请求参数不合法的异常捕获
    @ExceptionHandler(IllegalArgumentException.class)
    public  ResponseEntity<MessageDTO> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(400)
        );
    }
    //用户已存在异常捕获
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<MessageDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(409)
        );
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageDTO> handleUserNotFoundExceptionException(UserNotFoundException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    @ExceptionHandler(PasswordWrongException.class)
    public ResponseEntity<MessageDTO> handlePasswordWrongException(PasswordWrongException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<MessageDTO> handleCategoryNotFoundException(CategoryNotFoundException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    @ExceptionHandler(ToolNotFoundException.class)
    public ResponseEntity<MessageDTO> handleToolNotFoundExceptionException(ToolNotFoundException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    @ExceptionHandler(TokenUnauthorized.class)
    public ResponseEntity<MessageDTO> handleTokenUnauthorizedException(TokenUnauthorized e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<MessageDTO> handlePermissionDeniedException(PermissionDeniedException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(401)
        );
    }
    //AI工具已存在异常捕获
    @ExceptionHandler(ToolAlreadyExistsException.class)
    public ResponseEntity<MessageDTO> handleToolAlreadyExistsException(ToolAlreadyExistsException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(409)
        );
    }
    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity<MessageDTO> handleFileUploadFailedException(FileUploadFailedException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(500)
        );
    }
    @ExceptionHandler(NoResultGetException.class)
    public ResponseEntity<MessageDTO> handleNoResultException(NoResultGetException e){
        return new ResponseEntity<>(
                new MessageDTO(e.getMessage()),
                HttpStatus.valueOf(204)
        );
    }
}
