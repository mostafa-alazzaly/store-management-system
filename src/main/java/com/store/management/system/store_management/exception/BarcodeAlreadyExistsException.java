package com.store.management.system.store_management.exception;

public class BarcodeAlreadyExistsException extends RuntimeException {
    public BarcodeAlreadyExistsException(String message) {
        super(message);
    }
}
