package com.store.management.system.store_management.exception;

public class PaidAmountMustBePositiveException extends RuntimeException {
    public PaidAmountMustBePositiveException(String message) {
        super(message);
    }
}
