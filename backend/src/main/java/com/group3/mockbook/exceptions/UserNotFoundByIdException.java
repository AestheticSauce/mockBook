package com.group3.mockbook.exceptions;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException(int id) {
        super("User with id " + String.valueOf(id) + " not found");
    }
}
