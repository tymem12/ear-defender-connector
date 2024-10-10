package com.eardefender.mapper;

import com.eardefender.model.Analysis;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.response.AnalysisResponse;

import java.time.Duration;
import java.time.OffsetDateTime;

public class AnalysisMapper {
    private AnalysisMapper() {}

    public static AnalysisResponse toResponse(Analysis analysis) {
        AnalysisResponse response = new AnalysisResponse();

        response.setId(analysis.getId());
        response.setStatus(analysis.getStatus());
        response.setTimestamp(analysis.getTimestamp());
        response.setDuration(analysis.getDuration());
        response.setFileCount(analysis.getFileCount());

        if(analysis.getInputParams() != null) {
            response.setInputParams(response.getInputParams().clone());
        }

        if (analysis.getPredictionResults() != null) {
            response.setPredictions(analysis.getPredictionResults().stream().map(PredictionResult::clone).toList());
        }

        return response;
    }

    public static Analysis updateFromRequest(Analysis analysis, AnalysisRequest analysisRequest) {
        if(analysisRequest.getFinishTimestamp() != null) {
            OffsetDateTime startTimestamp = OffsetDateTime.parse(analysis.getTimestamp());
            OffsetDateTime finishTimestamp = OffsetDateTime.parse(analysisRequest.getFinishTimestamp());

            Duration duration = Duration.between(startTimestamp, finishTimestamp);
            long seconds = duration.getSeconds();

            analysis.setDuration(seconds);
        }

        if(analysisRequest.getInputParams() != null) {
            analysis.setInputParams(analysisRequest.getInputParams().clone());
        }

        if (analysisRequest.getPredictionResults() != null) {
            analysis.setPredictionResults(analysisRequest.getPredictionResults().stream().map(PredictionResult::clone).toList());
            analysis.setFileCount(analysisRequest.getPredictionResults().size());
        }

        if(analysisRequest.getStatus() != null) {
            analysis.setStatus(analysisRequest.getStatus());
        }

        return analysis;
    }
}
