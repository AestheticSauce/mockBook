package com.group3.mockbook.exceptions;

public class DuplicateEmailException  extends RuntimeException {
    public DuplicateEmailException(String email) {super("Email " + email + " already exists. Please login instead.");}
}
