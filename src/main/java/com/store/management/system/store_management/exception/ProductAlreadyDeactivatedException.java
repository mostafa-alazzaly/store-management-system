package com.store.management.system.store_management.exception;

public class ProductAlreadyDeactivatedException extends RuntimeException {
    public ProductAlreadyDeactivatedException(String message) {
        super(message);
    }
}
