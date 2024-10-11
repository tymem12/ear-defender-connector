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

public class BeginAnalysisRequestTest {

    private Validator validator;

    private BeginAnalysisRequest request;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = new BeginAnalysisRequest();

        request.setStartingPoint("start/point");
        request.setDepth(5);
        request.setMaxFiles(10);
        request.setModel("test-model");
    }

    @Test
    public void testValidAnalysisRequest() {
        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidBeginAnalysisRequest_BlankStartingPoint() {
        request.setStartingPoint("");

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Starting point must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NullDepth() {
        request.setDepth(null);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NegativeDepth() {
        request.setDepth(-1);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must be greater than 0", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NullMaxFiles() {
        request.setMaxFiles(null);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NegativeMaxFiles() {
        request.setMaxFiles(-1);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must be greater than 0", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_BlankModel() {
        request.setModel("");

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Model must not be blank", violation.getMessage());
    }
}
