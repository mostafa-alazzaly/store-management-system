package com.store.management.system.store_management.exception;

public class CannotDeleteActiveProductSupplierException extends RuntimeException {
    public CannotDeleteActiveProductSupplierException(String message) {
        super(message);
    }
}
