package com.store.management.system.store_management.exception;

public class InvoiceAlreadyCancelledException extends RuntimeException {
    public InvoiceAlreadyCancelledException(String message) {
        super(message);
    }
}
