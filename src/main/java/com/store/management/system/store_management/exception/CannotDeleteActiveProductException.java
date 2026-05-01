package com.store.management.system.store_management.exception;

public class CannotDeleteActiveProductException extends RuntimeException {
    public CannotDeleteActiveProductException(String message) {
        super(message);
    }
}
