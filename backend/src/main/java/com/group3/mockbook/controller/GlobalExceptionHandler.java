package com.group3.mockbook.controller;

import com.group3.mockbook.exceptions.DuplicateEmailException;
import com.group3.mockbook.exceptions.UserNotFoundByEmailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.group3.mockbook.exceptions.UserNotFoundByIdException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value= UserNotFoundByIdException.class)
    public ResponseEntity<String> handleUserNotFoundByIdException(UserNotFoundByIdException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserNotFoundByEmailException.class)
    public ResponseEntity<String> handleUserNotFoundByEmailException(UserNotFoundByEmailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
