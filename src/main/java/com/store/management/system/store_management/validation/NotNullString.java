package com.store.management.system.store_management.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = NotNullStringValidator.class)
public @interface NotNullString {

    String message() default "Field can't be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


