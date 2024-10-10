package com.eardefender.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SegmentPredictionTest {
    private Validator validator;

    private SegmentPrediction model;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        model = new SegmentPrediction();

        model.setLabel(1);
        model.setSegmentNumber(1);
    }

    @Test
    public void testValidSegmentPrediction() {
        Set<ConstraintViolation<SegmentPrediction>> violations = validator.validate(model);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidSegmentPredictions_NullLabel() {
        model.setLabel(null);

        Set<ConstraintViolation<SegmentPrediction>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<SegmentPrediction> violation = violations.iterator().next();
        assertEquals("Label must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidSegmentPredictions_NullSegmentNumber() {
        model.setSegmentNumber(null);

        Set<ConstraintViolation<SegmentPrediction>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<SegmentPrediction> violation = violations.iterator().next();
        assertEquals("Segment number must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidSegmentPredictions_NegativeSegmentNumber() {
        model.setSegmentNumber(-3);

        Set<ConstraintViolation<SegmentPrediction>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<SegmentPrediction> violation = violations.iterator().next();
        assertEquals("Segment number must not be negative", violation.getMessage());
    }
}