package com.store.management.system.store_management.exception;

public class PaymentReferenceIsRequiredException extends RuntimeException {
    public PaymentReferenceIsRequiredException(String message) {
        super(message);
    }
}
