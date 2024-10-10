package com.eardefender.model.request;

import com.eardefender.model.InputParams;
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

public class AnalysisRequestTest {

    private Validator validator;

    private AnalysisRequest request;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = new AnalysisRequest();

        InputParams inputParams = new InputParams();
        inputParams.setDepth(1);
        inputParams.setStartingPoint("startingPoint");
        inputParams.setModel("model");
        inputParams.setMaxFiles(100);

        request.setStatus("PROCESSING");
        request.setFinishTimestamp("2005-07-16T19:20:40+01:00");
        request.setPredictionResults(List.of());
        request.setInputParams(inputParams);
    }

    @Test
    public void testValidAnalysisRequest() {
        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidAnalysisRequest_InvalidStatus() {
        request.setStatus("INVALID");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Status must be one of the following: DOWNLOADING, PROCESSING, FINISHED", violation.getMessage());
    }

    @Test
    public void testInvalidAnalysisRequest_InvalidTimestamp() {
        request.setFinishTimestamp("Invalid");

        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AnalysisRequest> violation = violations.iterator().next();
        assertEquals("Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format", violation.getMessage());
    }
}
