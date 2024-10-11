package com.eardefender.exception;

public class AnalysisNotFoundException extends RuntimeException {

    public AnalysisNotFoundException(String id) {
        super("Analysis with id " + id + " not found");
    }
}
