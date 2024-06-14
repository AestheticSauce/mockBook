package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group3.mockbook.exceptions.UserNotFoundByIdException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value= UserNotFoundByIdException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundByIdException e) {
        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
