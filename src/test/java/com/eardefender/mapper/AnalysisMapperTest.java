package com.eardefender.mapper;

import com.eardefender.model.Analysis;
import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.response.AnalysisResponse;
import com.eardefender.model.response.DetailedAnalysisResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalysisMapperTest {

    @Test
    void toDetailedResponse_validAnalysis_responseMapped() {
        Analysis analysis = new Analysis();
        analysis.setId("testId");
        analysis.setStatus("COMPLETED");
        analysis.setTimestamp("2024-10-10T10:00:00+00:00");
        analysis.setDuration(3600L);
        analysis.setFileCount(5);
        analysis.setDeepfakeFileCount(1);
        analysis.setInputParams(new InputParams());
        PredictionResult predictionResult = new PredictionResult();
        analysis.setPredictionResults(List.of(predictionResult));

        DetailedAnalysisResponse response = AnalysisMapper.toDetailedResponse(analysis);

        assertEquals("testId", response.getId());
        assertEquals("COMPLETED", response.getStatus());
        assertEquals("2024-10-10T10:00:00+00:00", response.getTimestamp());
        assertEquals(3600L, response.getDuration());
        assertEquals(5, response.getFileCount());
        analysis.setDeepfakeFileCount(1);
        assertNotNull(response.getInputParams());
        assertNotNull(response.getPredictionResults());
        assertEquals(1, response.getPredictionResults().size());
        assertEquals(predictionResult, response.getPredictionResults().get(0));
    }

    @Test
    void toDetailedResponse_nullInputParamsAndPredictionResults_responseWithNullFields() {
        Analysis analysis = new Analysis();
        analysis.setId("testId");
        analysis.setStatus("IN_PROGRESS");
        analysis.setTimestamp("2024-10-10T10:00:00+00:00");
        analysis.setDuration(3600L);
        analysis.setFileCount(3);
        analysis.setDeepfakeFileCount(2);
        analysis.setInputParams(null);
        analysis.setPredictionResults(null);

        DetailedAnalysisResponse response = AnalysisMapper.toDetailedResponse(analysis);

        assertEquals("testId", response.getId());
        assertEquals("IN_PROGRESS", response.getStatus());
        assertEquals("2024-10-10T10:00:00+00:00", response.getTimestamp());
        assertEquals(3600L, response.getDuration());
        assertEquals(3, response.getFileCount());
        assertEquals(2, response.getDeepfakeFileCount());
        assertNull(response.getInputParams());
        assertNull(response.getPredictionResults());
    }

    @Test
    void updateFromRequest_validRequest_analysisUpdated() {
        Analysis analysis = new Analysis();
        analysis.setTimestamp("2024-10-10T10:00:00+00:00");
        analysis.setStatus("IN_PROGRESS");
        PredictionResult predictionResult = new PredictionResult();
        analysis.setPredictionResults(List.of(predictionResult));

        AnalysisRequest request = new AnalysisRequest();
        request.setPredictionResults(List.of(predictionResult));

        Analysis updatedAnalysis = AnalysisMapper.updateFromRequest(analysis, request);

        assertEquals("IN_PROGRESS", updatedAnalysis.getStatus());
        assertNull(updatedAnalysis.getDuration());
        assertEquals(1, updatedAnalysis.getFileCount());
        assertNotNull(updatedAnalysis.getPredictionResults());
        assertEquals(1, updatedAnalysis.getPredictionResults().size());
        assertEquals(predictionResult, updatedAnalysis.getPredictionResults().get(0));
    }

    @Test
    void updateFromRequest_nullFieldsInRequest_fieldsRemainUnchanged() {
        Analysis analysis = new Analysis();
        analysis.setTimestamp("2024-10-10T10:00:00+00:00");
        analysis.setStatus("IN_PROGRESS");
        analysis.setDuration(1800L);
        PredictionResult predictionResult = new PredictionResult();
        analysis.setPredictionResults(List.of(predictionResult));
        analysis.setFileCount(1);

        AnalysisRequest request = new AnalysisRequest();
        request.setPredictionResults(null);

        Analysis updatedAnalysis = AnalysisMapper.updateFromRequest(analysis, request);

        assertEquals("IN_PROGRESS", updatedAnalysis.getStatus());
        assertEquals(1800L, updatedAnalysis.getDuration());
        assertEquals(1, updatedAnalysis.getFileCount());
        assertNotNull(updatedAnalysis.getPredictionResults());
    }

    @Test
    void toBaseResponse_validAnalysis_responseMapped() {
        Analysis analysis = new Analysis();
        analysis.setId("testId");
        analysis.setStatus("COMPLETED");
        analysis.setTimestamp("2024-10-10T10:00:00+00:00");
        analysis.setDuration(3600L);
        analysis.setFileCount(5);
        analysis.setInputParams(new InputParams());
        analysis.setDeepfakeFileCount(3);
        PredictionResult predictionResult = new PredictionResult();
        analysis.setPredictionResults(List.of(predictionResult));

        AnalysisResponse response = AnalysisMapper.toBaseResponse(analysis);

        assertEquals("testId", response.getId());
        assertEquals("COMPLETED", response.getStatus());
        assertEquals("2024-10-10T10:00:00+00:00", response.getTimestamp());
        assertEquals(3600L, response.getDuration());
        assertEquals(3, response.getDeepfakeFileCount());
        assertEquals(5, response.getFileCount());
        assertNotNull(response.getInputParams());
    }
}