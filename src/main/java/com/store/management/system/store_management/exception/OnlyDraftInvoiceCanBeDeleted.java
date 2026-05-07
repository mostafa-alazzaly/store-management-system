package com.store.management.system.store_management.exception;

public class OnlyDraftInvoiceCanBeDeleted extends RuntimeException {
    public OnlyDraftInvoiceCanBeDeleted(String message) {
        super(message);
    }
}
