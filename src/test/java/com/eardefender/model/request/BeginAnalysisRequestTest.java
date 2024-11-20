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
        request.setModel("mesonet");
        request.setMaxPages(10);
        request.setMaxTimePerFile(50);
        request.setMaxTotalTime(200);
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
    public void testInvalidBeginAnalysisRequest_DepthBelowRange() {
        request.setDepth(0);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must be between 1 and 5", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_DepthAboveRange() {
        request.setDepth(12);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Depth must be between 1 and 5", violation.getMessage());
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
    public void testInvalidBeginAnalysisRequest_MaxFilesBelowRange() {
        request.setMaxFiles(-1);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must be between 1 and 50", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxFilesAboveRange() {
        request.setMaxFiles(51);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max files must be between 1 and 50", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_BlankModel() {
        request.setModel("");

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Model must not be blank", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_InvalidModel() {
        request.setModel("some-random-model");

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Invalid model", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NullMaxPages() {
        request.setMaxPages(null);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max pages must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxPagesBelowRange() {
        request.setMaxPages(-1);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max pages must be between 1 and 1000", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxPagesAboveRange() {
        request.setMaxPages(1111);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max pages must be between 1 and 1000", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NullMaxTimePerFile() {
        request.setMaxTimePerFile(null);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max time per file must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxTimePerFileBelowRange() {
        request.setMaxTimePerFile(-1);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max time per file must be between 1 and 120", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxTimePerFileAboveRange() {
        request.setMaxTimePerFile(151);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max time per file must be between 1 and 120", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_NullMaxTotalTime() {
        request.setMaxTotalTime(null);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max total time must not be null", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxTotalTimeBelowRange() {
        request.setMaxTotalTime(-12);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max total time must be between 1 and 3600", violation.getMessage());
    }

    @Test
    public void testInvalidBeginAnalysisRequest_MaxTotalTimeAboveRange() {
        request.setMaxTotalTime(12345);

        Set<ConstraintViolation<BeginAnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<BeginAnalysisRequest> violation = violations.iterator().next();
        assertEquals("Max total time must be between 1 and 3600", violation.getMessage());
    }
}
