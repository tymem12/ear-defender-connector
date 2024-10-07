package com.eardefender.model.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalysisRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidAnalysisRequest() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(5);
        request.setMaxFiles(10);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidAnalysisRequest_BlankStartingPoint() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("");
        request.setDepth(5);
        request.setMaxFiles(10);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Starting point must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_NullDepth() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(null);
        request.setMaxFiles(10);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_NegativeDepth() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(-1);
        request.setMaxFiles(10);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must be greater than 0", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_NullMaxFiles() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(5);
        request.setMaxFiles(null);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_NegativeMaxFiles() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(5);
        request.setMaxFiles(-1);
        request.setModel("test-model");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must be greater than 0", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_BlankModel() {
        AnalysisRequest request = new AnalysisRequest();
        request.setStartingPoint("start/point");
        request.setDepth(5);
        request.setMaxFiles(10);
        request.setModel("");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Model must not be blank", violation.getMessage());
    }
}
