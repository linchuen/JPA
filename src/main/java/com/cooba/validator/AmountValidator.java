package com.cooba.validator;

import com.cooba.annotation.AmountValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class AmountValidator implements ConstraintValidator<AmountValidation, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return new BigDecimal(value).compareTo(BigDecimal.ZERO) > 0;
    }
}
