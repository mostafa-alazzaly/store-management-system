package com.store.management.system.store_management.exception;

public class CategoryAlreadyDeactivatedException extends RuntimeException {
    public CategoryAlreadyDeactivatedException(String message) {
        super(message);
    }
}
