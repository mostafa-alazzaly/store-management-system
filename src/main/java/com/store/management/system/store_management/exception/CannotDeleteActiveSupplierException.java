package com.store.management.system.store_management.exception;

public class CannotDeleteActiveSupplierException extends RuntimeException {
    public CannotDeleteActiveSupplierException(String message) {
        super(message);
    }
}
