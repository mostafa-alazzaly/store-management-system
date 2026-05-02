package com.store.management.system.store_management.exception;

public class QuantityMustBePositiveException extends RuntimeException {
    public QuantityMustBePositiveException(String message) {
        super(message);
    }
}
