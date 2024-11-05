package com.eardefender.mapper;

import com.eardefender.model.PredictionResult;
import com.eardefender.model.SegmentPrediction;
import com.eardefender.model.request.PredictionResultRequest;
import com.eardefender.model.response.PredictionResultResponse;

import java.util.List;

public class PredictionResultMapper {
    private PredictionResultMapper() {}

    public static PredictionResult toModel(PredictionResultRequest request) {
        List<SegmentPrediction> segmentPredictions = request.getModelPredictions() == null
                ? null
                : request.getModelPredictions().stream().map(SegmentPrediction::clone).toList();

        return PredictionResult.builder()
                .model(request.getModel())
                .link(request.getLink())
                .timestamp(request.getTimestamp())
                .modelPredictions(segmentPredictions)
                .build();
    }

    public static PredictionResultResponse toResponse(PredictionResult predictionResult) {
        List<SegmentPrediction> segmentPredictions = predictionResult.getModelPredictions() == null
                ? null
                : predictionResult.getModelPredictions().stream().map(SegmentPrediction::clone).toList();

        return PredictionResultResponse.builder()
                .model(predictionResult.getModel())
                .link(predictionResult.getLink())
                .timestamp(predictionResult.getTimestamp())
                .modelPredictions(segmentPredictions)
                .build();
    }
}
