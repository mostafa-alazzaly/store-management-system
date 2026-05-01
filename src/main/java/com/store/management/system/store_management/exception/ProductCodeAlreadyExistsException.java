package com.store.management.system.store_management.exception;

public class ProductCodeAlreadyExistsException extends RuntimeException {
    public ProductCodeAlreadyExistsException(String message) {
        super(message);
    }
}
