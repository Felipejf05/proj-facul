package com.proj.facul.exception;

public class DuplicatePhoneException extends RuntimeException{
    public DuplicatePhoneException(String message) {
        super(message);
    }
}