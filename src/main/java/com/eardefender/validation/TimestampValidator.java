package com.eardefender.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class TimestampValidator implements ConstraintValidator<Timestamp, String> {

    private static final String TIMESTAMP_PATTERN =
            "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(?:Z|[+-]\\d{2}:\\d{2})$";
    private static final Pattern pattern = Pattern.compile(TIMESTAMP_PATTERN);

    @Override
    public void initialize(Timestamp constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return pattern.matcher(value).matches();
    }
}

