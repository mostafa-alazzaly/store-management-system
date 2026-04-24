package com.store.management.system.store_management.exception;

public class UserAlreadyAssignedToEmployeeException extends RuntimeException {
    public UserAlreadyAssignedToEmployeeException(String message) {
        super(message);
    }
}
