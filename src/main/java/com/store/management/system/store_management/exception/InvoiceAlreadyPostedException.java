package com.store.management.system.store_management.exception;

public class InvoiceAlreadyPostedException extends RuntimeException {
    public InvoiceAlreadyPostedException(String message) {
        super(message);
    }
}
