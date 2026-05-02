package com.store.management.system.store_management.exception;

public class InventoryTransactionAlreadyReversedException extends RuntimeException {
    public InventoryTransactionAlreadyReversedException(String message) {
        super(message);
    }
}
