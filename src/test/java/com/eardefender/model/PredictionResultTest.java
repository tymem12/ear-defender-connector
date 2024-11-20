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
        model.setScore(0.45f);
        model.setLabel(1);

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
    public void testInvalidPredictionResult_NullScore() {
        model.setScore(null);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Score must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_ScoreAboveRange() {
        model.setScore(1.01f);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Score must be between 0 and 1", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_ScoreBelowRange() {
        model.setScore(-0.01f);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Score must be between 0 and 1", violation.getMessage());
    }

    @Test
    public void testValidPredictionResult_ScoreMaxValue() {
        model.setScore(1.0f);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(0, violations.size());
    }

    @Test
    public void testValidPredictionResult_ScoreMinValue() {
        model.setScore(0.0f);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidPredictionResult_NullLabel() {
        model.setLabel(null);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Label must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_LabelAboveRange() {
        model.setLabel(2);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Label must be between 0 and 1", violation.getMessage());
    }

    @Test
    public void testInvalidPredictionResult_LabelBelowRange() {
        model.setLabel(-1);

        Set<ConstraintViolation<PredictionResult>> violations = validator.validate(model);

        assertEquals(1, violations.size());
        ConstraintViolation<PredictionResult> violation = violations.iterator().next();
        assertEquals("Label must be between 0 and 1", violation.getMessage());
    }
}