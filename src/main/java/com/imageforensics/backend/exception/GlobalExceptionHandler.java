package com.imageforensics.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imageforensics.backend.exception.InvalidImageException;
import com.imageforensics.backend.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidImageException.class)
    public ResponseEntity<String> handleInvalidImage(InvalidImageException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}