package com.store.management.system.store_management.exception;

public class NotEnoughStockQuantityException extends RuntimeException {
    public NotEnoughStockQuantityException(String message) {
        super(message);
    }
}
