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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddPredictionRequestTest {

    private Validator validator;

    private AddPredictionsRequest request;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        request = new AddPredictionsRequest();

        PredictionResult predictionResult = new PredictionResult();
        predictionResult.setModelPredictions(List.of());
        predictionResult.setModel("example-model");
        predictionResult.setTimestamp("1997-07-16T19:20:30+01:00");
        predictionResult.setLink("link");

        request.setPredictionResults(List.of(predictionResult));
    }

    @Test
    public void testValidAddPredictionsRequest() {
        Set<ConstraintViolation<AddPredictionsRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No validation errors expected for valid request");
    }

    @Test
    public void testInvalidAddPredictionsRequest_EmptyPredictions() {
        request.setPredictionResults(List.of());

        Set<ConstraintViolation<AddPredictionsRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AddPredictionsRequest> violation = violations.iterator().next();
        assertEquals("Prediction results must not be empty", violation.getMessage());
    }

    @Test
    public void testInvalidAddPredictionsRequest_nullPredictions() {
        request.setPredictionResults(null);

        Set<ConstraintViolation<AddPredictionsRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<AddPredictionsRequest> violation = violations.iterator().next();
        assertEquals("Prediction results must not be empty", violation.getMessage());
    }
}
