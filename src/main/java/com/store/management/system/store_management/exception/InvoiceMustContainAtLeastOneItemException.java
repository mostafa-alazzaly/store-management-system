package com.store.management.system.store_management.exception;

public class InvoiceMustContainAtLeastOneItemException extends RuntimeException {
    public InvoiceMustContainAtLeastOneItemException(String message) {
        super(message);
    }
}
