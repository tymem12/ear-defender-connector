package com.eardefender.mapper;

import com.eardefender.model.PredictionResult;
import com.eardefender.model.SegmentPrediction;
import com.eardefender.model.request.PredictionResultRequest;
import com.eardefender.model.response.PredictionResultResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PredictionResultMapperTest {

    @Test
    void toModel_validRequest_modelMapped() {
        PredictionResultRequest request = new PredictionResultRequest();
        request.setModel("testModel");
        request.setLink("testLink");
        request.setTimestamp("timestamp");
        SegmentPrediction prediction = new SegmentPrediction();
        request.setModelPredictions(List.of(prediction));

        PredictionResult result = PredictionResultMapper.toModel(request);

        assertEquals("testModel", result.getModel());
        assertEquals("testLink", result.getLink());
        assertEquals(request.getTimestamp(), result.getTimestamp());
        assertNotNull(result.getModelPredictions());
        assertEquals(1, result.getModelPredictions().size());
        assertEquals(prediction, result.getModelPredictions().get(0));
    }

    @Test
    void toModel_nullModelPredictions_emptyPredictionsInModel() {
        PredictionResultRequest request = new PredictionResultRequest();
        request.setModel("testModel");
        request.setLink("testLink");
        request.setTimestamp("timestamp");
        request.setModelPredictions(null);

        PredictionResult result = PredictionResultMapper.toModel(request);

        assertEquals("testModel", result.getModel());
        assertEquals("testLink", result.getLink());
        assertEquals(request.getTimestamp(), result.getTimestamp());
        assertNull(result.getModelPredictions());
    }

    @Test
    void toResponse_validPredictionResult_responseMapped() {
        PredictionResult predictionResult = new PredictionResult();
        predictionResult.setModel("testModel");
        predictionResult.setLink("testLink");
        predictionResult.setTimestamp("timestamp");
        SegmentPrediction prediction = new SegmentPrediction();
        predictionResult.setModelPredictions(List.of(prediction));

        PredictionResultResponse response = PredictionResultMapper.toResponse(predictionResult);

        assertEquals("testModel", response.getModel());
        assertEquals("testLink", response.getLink());
        assertEquals(predictionResult.getTimestamp(), response.getTimestamp());
        assertNotNull(response.getModelPredictions());
        assertEquals(1, response.getModelPredictions().size());
        assertEquals(prediction, response.getModelPredictions().get(0));
    }

    @Test
    void toResponse_nullModelPredictions_emptyPredictionsInResponse() {
        PredictionResult predictionResult = new PredictionResult();
        predictionResult.setModel("testModel");
        predictionResult.setLink("testLink");
        predictionResult.setTimestamp("timestamp");
        predictionResult.setModelPredictions(null);

        PredictionResultResponse response = PredictionResultMapper.toResponse(predictionResult);

        assertEquals("testModel", response.getModel());
        assertEquals("testLink", response.getLink());
        assertEquals(predictionResult.getTimestamp(), response.getTimestamp());
        assertNull(response.getModelPredictions()); // Null should remain null
    }
}
