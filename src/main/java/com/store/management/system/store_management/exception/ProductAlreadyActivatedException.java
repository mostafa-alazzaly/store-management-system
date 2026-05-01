package com.store.management.system.store_management.exception;

public class ProductAlreadyActivatedException extends RuntimeException {
    public ProductAlreadyActivatedException(String message) {
        super(message);
    }
}
