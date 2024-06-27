package com.group3.mockbook.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(int id) {
        super("Entity with id " + String.valueOf(id) + " not found");
    }

    public EntityNotFoundException(Object obj) {
        super("Entity " + obj.toString() + " not found");
    }
}
