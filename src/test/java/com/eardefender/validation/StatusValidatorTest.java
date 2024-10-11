package com.eardefender.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class StatusValidatorTest {

    private StatusValidator statusValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        statusValidator = new StatusValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"DOWNLOADING", "PROCESSING", "FINISHED"})
    void isValid_ValidStatus_ReturnsTrue(String status) {
        assertTrue(statusValidator.isValid(status, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"downloading", "PROCESSING ", "FINISHED4", "invalid"})
    void isValid_InvalidStatus_ReturnsFalse(String status) {
        assertFalse(statusValidator.isValid(status, context));
    }

    @Test
    void isValid_BlankStatus_ReturnsTrue() {
        assertTrue(statusValidator.isValid("", context));
    }

    @Test
    void isValid_nullStatus_ReturnsTrue() {
        assertTrue(statusValidator.isValid(null, context));
    }
}
