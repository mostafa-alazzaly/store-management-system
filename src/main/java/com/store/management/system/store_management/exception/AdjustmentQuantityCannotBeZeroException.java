package com.store.management.system.store_management.exception;

public class AdjustmentQuantityCannotBeZeroException extends RuntimeException {
    public AdjustmentQuantityCannotBeZeroException(String message) {
        super(message);
    }
}
