package com.store.management.system.store_management.exception;

public class CategoryCannotBeParentOfItselfException extends RuntimeException {
    public CategoryCannotBeParentOfItselfException(String message) {
        super(message);
    }
}
