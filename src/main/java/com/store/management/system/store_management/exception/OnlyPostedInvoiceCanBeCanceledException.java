package com.store.management.system.store_management.exception;

public class OnlyPostedInvoiceCanBeCanceledException extends RuntimeException {
    public OnlyPostedInvoiceCanBeCanceledException(String message) {
        super(message);
    }
}
