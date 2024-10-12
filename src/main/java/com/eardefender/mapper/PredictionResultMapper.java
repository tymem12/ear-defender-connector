package com.eardefender.mapper;

import com.eardefender.model.PredictionResult;
import com.eardefender.model.SegmentPrediction;
import com.eardefender.model.request.PredictionResultRequest;
import com.eardefender.model.response.PredictionResultResponse;

public class PredictionResultMapper {
    private PredictionResultMapper() {}

    public static PredictionResult toModel(PredictionResultRequest request) {
        PredictionResult result = new PredictionResult();

        result.setModel(request.getModel());
        result.setLink(request.getLink());
        result.setTimestamp(request.getTimestamp());

        if (request.getModelPredictions() != null) {
            result.setModelPredictions(request.getModelPredictions().stream().map(SegmentPrediction::clone).toList());
        }

        return result;
    }

    public static PredictionResultResponse toResponse(PredictionResult predictionResult) {
        PredictionResultResponse response = new PredictionResultResponse();

        response.setModel(predictionResult.getModel());
        response.setLink(predictionResult.getLink());
        response.setTimestamp(predictionResult.getTimestamp());

        if (predictionResult.getModelPredictions() != null) {
            response.setModelPredictions(predictionResult.getModelPredictions().stream().map(SegmentPrediction::clone).toList());
        }

        return response;
    }
}
