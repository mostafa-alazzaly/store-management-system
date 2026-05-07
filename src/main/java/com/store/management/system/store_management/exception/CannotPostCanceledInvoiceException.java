package com.store.management.system.store_management.exception;

public class CannotPostCanceledInvoiceException extends RuntimeException {
    public CannotPostCanceledInvoiceException(String message) {
        super(message);
    }
}
