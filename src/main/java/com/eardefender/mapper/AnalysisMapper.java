package com.eardefender.mapper;

import com.eardefender.model.Analysis;
import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.response.AnalysisResponse;
import com.eardefender.model.response.DetailedAnalysisResponse;

import java.util.List;

public class AnalysisMapper {
    private AnalysisMapper() {}

    public static AnalysisResponse toBaseResponse(Analysis analysis) {
        InputParams inputParams = analysis.getInputParams() == null
                ? null
                : analysis.getInputParams().clone();

        return AnalysisResponse.builder()
                .id(analysis.getId())
                .status(analysis.getStatus())
                .timestamp(analysis.getTimestamp())
                .duration(analysis.getDuration())
                .fileCount(analysis.getFileCount())
                .deepfakeFileCount(analysis.getDeepfakeFileCount())
                .inputParams(inputParams)
                .build();
    }

    public static DetailedAnalysisResponse toDetailedResponse(Analysis analysis) {
        InputParams inputParams = analysis.getInputParams() == null
                ? null
                : analysis.getInputParams().clone();

        List<PredictionResult> predictionResults = analysis.getPredictionResults() == null
                ? null
                : analysis.getPredictionResults().stream().map(PredictionResult::clone).toList();

        return DetailedAnalysisResponse.builder()
                .id(analysis.getId())
                .status(analysis.getStatus())
                .timestamp(analysis.getTimestamp())
                .duration(analysis.getDuration())
                .fileCount(analysis.getFileCount())
                .deepfakeFileCount(analysis.getDeepfakeFileCount())
                .inputParams(inputParams)
                .predictionResults(predictionResults)
                .build();
    }

    public static Analysis updateFromRequest(Analysis analysis, AnalysisRequest analysisRequest) {
        if (analysisRequest.getPredictionResults() != null) {
            analysis.setPredictionResults(analysisRequest.getPredictionResults().stream().map(PredictionResult::clone).toList());
            analysis.setFileCount(analysisRequest.getPredictionResults().size());
        }

        return analysis;
    }
}
