package com.cooba.annotation;

import com.cooba.validator.AmountValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Constraint(validatedBy = AmountValidator.class)
public @interface AmountValidation {
    String message() default "0.0";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
