package com.eardefender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.eardefender.constants.EarDefenderConstants.MODEL_MESONET;
import static com.eardefender.constants.EarDefenderConstants.MODEL_WAV2VEC;

public class ModelValidator implements ConstraintValidator<Model, String> {

    @Override
    public void initialize(Model constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        return value.equals(MODEL_MESONET) || value.equals(MODEL_WAV2VEC);
    }
}

