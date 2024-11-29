package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnalysisRequestTest {

    private Validator validator;

    private AnalysisRequest request;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = new AnalysisRequest();

        request.setPredictionResults(List.of(new PredictionResult()));
    }

    @Test
    public void testValidAnalysisRequest() {
        Set<ConstraintViolation<AnalysisRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }
}
