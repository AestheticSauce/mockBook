package com.group3.mockbook;

import com.group3.mockbook.controller.GlobalExceptionHandler;
import com.group3.mockbook.exceptions.DuplicateEmailException;
import com.group3.mockbook.exceptions.UserNotFoundByEmailException;
import com.group3.mockbook.exceptions.UserNotFoundByIdException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleUserNotFoundByIdException() {
        UserNotFoundByIdException exception = new UserNotFoundByIdException(0);
        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundByIdException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }

    @Test
    public void testHandleUserNotFoundByEmailException() {
        UserNotFoundByEmailException exception = new UserNotFoundByEmailException("email@dal.ca");
        ResponseEntity<String> response = globalExceptionHandler.handleUserNotFoundByEmailException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }

    @Test
    public void testHandleDuplicateEmailException() {
        DuplicateEmailException exception = new DuplicateEmailException("email@dal.ca");
        ResponseEntity<String> response = globalExceptionHandler.handleDuplicateEmailException(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }
}
