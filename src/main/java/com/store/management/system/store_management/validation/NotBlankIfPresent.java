package com.store.management.system.store_management.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy= NotBlankIfPresentValidator.class)
@Target ({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankIfPresent {
    String message() default "Field can't be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
