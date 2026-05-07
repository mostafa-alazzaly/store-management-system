package com.store.management.system.store_management.exception;

public class InvoiceItemDoesNotBelongToInvoiceException extends RuntimeException {
    public InvoiceItemDoesNotBelongToInvoiceException(String message) {
        super(message);
    }
}
