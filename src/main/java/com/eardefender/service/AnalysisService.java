package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;

import java.util.List;

public interface AnalysisService {
    void beginAnalysis(BeginAnalysisRequest request);
    Analysis getById(String id) throws AnalysisNotFoundException;
    List<Analysis> getAll();
    Analysis update(String id, AnalysisRequest analysisRequest) throws AnalysisNotFoundException;
    void deleteById(String id) throws AnalysisNotFoundException;
    Analysis addPredictionResults(String id, AddPredictionsRequest addPredictionsRequest) throws AnalysisNotFoundException;
}
