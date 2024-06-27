package com.group3.mockbook.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import com.group3.mockbook.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(value=BadCredentialsException.class)
    public ResponseEntity<String> handleUserNotFoundException(BadCredentialsException e) {
        ResponseEntity<String> response = new ResponseEntity<String>("YOU ARE NOT AUTHORIZED! SET YOUR JWT BEARER TOKEN!", HttpStatus.UNAUTHORIZED);
        return response;
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }
}
