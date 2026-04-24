package com.store.management.system.store_management.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullStringValidator implements ConstraintValidator<NotNullString, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value==null) {
            return true;
        }
        return !value.trim().equalsIgnoreCase("null");
    }
}
