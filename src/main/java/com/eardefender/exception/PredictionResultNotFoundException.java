package com.eardefender.exception;

public class PredictionResultNotFoundException extends RuntimeException {

    public PredictionResultNotFoundException(String model, String link) {
        super("Prediction result with model " + model + " not found for link " + link);
    }
}
