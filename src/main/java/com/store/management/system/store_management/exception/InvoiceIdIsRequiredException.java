package com.store.management.system.store_management.exception;

public class InvoiceIdIsRequiredException extends RuntimeException {
    public InvoiceIdIsRequiredException(String message) {
        super(message);
    }
}
