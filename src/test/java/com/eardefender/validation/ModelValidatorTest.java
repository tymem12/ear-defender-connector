package com.eardefender.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ModelValidatorTest {

    private ModelValidator modelValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        modelValidator = new ModelValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"mesonet", "wav2vec"})
    void isValid_ValidModel_ReturnsTrue(String timestamp) {
        assertTrue(modelValidator.isValid(timestamp, context));
    }

    @ParameterizedTest
    @ValueSource(strings = {"example", "invalid"})
    void isValid_InvalidModel_ReturnsFalse(String timestamp) {
        assertFalse(modelValidator.isValid(timestamp, context));
    }

    @Test
    void isValid_BlankTimestamp_ReturnsTrue() {
        assertTrue(modelValidator.isValid("", context));
    }

    @Test
    void isValid_nullTimestamp_ReturnsTrue() {
        assertTrue(modelValidator.isValid(null, context));
    }
}