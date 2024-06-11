package com.group3.mockbook.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User with id " + String.valueOf(id) + " not found");
    }
}
