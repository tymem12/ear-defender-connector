package com.eardefender.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TimestampValidatorTest {

    private TimestampValidator timestampValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        timestampValidator = new TimestampValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1997-07-16T19:20:30+01:00", "1990-07-16T20:20:40+01:00", "2023-01-11T20:20:00+19:00"})
    void isValid_ValidTimestamp_ReturnsTrue(String timestamp) {
        assertTrue(timestampValidator.isValid(timestamp, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1997-07-16T19:20:30", "1997-07-16 19:20:30+01:00 ", "invalid"})
    void isValid_InvalidTimestamp_ReturnsFalse(String timestamp) {
        assertFalse(timestampValidator.isValid(timestamp, context));
    }

    @Test
    void isValid_BlankTimestamp_ReturnsTrue() {
        assertTrue(timestampValidator.isValid("", context));
    }

    @Test
    void isValid_nullTimestamp_ReturnsTrue() {
        assertTrue(timestampValidator.isValid(null, context));
    }
}