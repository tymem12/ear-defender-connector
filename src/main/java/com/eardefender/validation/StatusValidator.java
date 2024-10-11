package com.eardefender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.eardefender.constants.EarDefenderConstants.*;

public class StatusValidator implements ConstraintValidator<Status, String> {

    @Override
    public void initialize(Status constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.equals(STATUS_DOWNLOADING)
                || value.equals(STATUS_PROCESSING)
                || value.equals(STATUS_FINISHED);
    }
}

