package com.store.management.system.store_management.exception;

public class OnlyDraftInvoiceCanBeUpdatedException extends RuntimeException {
    public OnlyDraftInvoiceCanBeUpdatedException(String message) {
        super(message);
    }
}
