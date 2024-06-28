package com.group3.mockbook.controller;

import com.group3.mockbook.exceptions.DuplicateEmailException;
import com.group3.mockbook.exceptions.UserNotFoundByEmailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=EntityNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(EntityNotFoundException e) {
        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(value=BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        ResponseEntity<String> response = new ResponseEntity<String>("YOU ARE NOT AUTHORIZED! CHECK YOUR CREDENTIALS!", HttpStatus.UNAUTHORIZED);
        return response;
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        ResponseEntity<String> response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }
}
