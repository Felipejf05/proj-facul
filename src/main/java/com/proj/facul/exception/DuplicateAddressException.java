package com.proj.facul.exception;

public class DuplicateAddressException extends RuntimeException{
    public DuplicateAddressException(String message) {
        super(message);
    }
}
