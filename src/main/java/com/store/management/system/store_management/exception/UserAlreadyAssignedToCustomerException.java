package com.store.management.system.store_management.exception;

public class UserAlreadyAssignedToCustomerException extends RuntimeException {
    public UserAlreadyAssignedToCustomerException(String message) {
        super(message);
    }
}
