package com.eardefender.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PredictionResultTest {
    private Validator validator;

    private PredictionResult model;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        model = new PredictionResult();

        model.setLink("example-link");
        model.setTimestamp("1991-07-16T20:20:40+01:00");
        model.setModel("example-model");

        SegmentPrediction segmentPrediction = new SegmentPrediction();
        segmentPrediction.setLabel(1);
        segmentPrediction.setSegmentNumber(1);

        model.setModelPredictions(List.of(segmentPrediction));
    }

    @Test
    public void testValidPredictionResult() {
        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidPredictionResult_NullModel() {
        model.setModel(null);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Model must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_BlankModel() {
        model.setModel("");

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Model must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_NullLink() {
        model.setLink(null);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Link must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_BlankLink() {
        model.setLink("");

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Link must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_NullTimestamp() {
        model.setTimestamp(null);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Timestamp must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_BlankTimestamp() {
        model.setTimestamp("");

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Timestamp must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_InvalidTimestamp() {
        model.setTimestamp("invalid");

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format", violation.getMessage());
    }
}