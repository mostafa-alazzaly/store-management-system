package com.store.management.system.store_management.exception;

public class PaymentReferenceAlreadyExistException extends RuntimeException {
    public PaymentReferenceAlreadyExistException(String message) {
        super(message);
    }
}
