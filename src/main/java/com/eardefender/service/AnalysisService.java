package com.eardefender.service;

import com.eardefender.model.Analysis;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;

import java.util.List;

public interface AnalysisService {
    void beginAnalysis(BeginAnalysisRequest request);
    Analysis getById(String id);
    List<Analysis> getAll();
    Analysis update(String id, AnalysisRequest analysisRequest);
    void deleteById(String id);
    Analysis addPredictionResults(String id, AddPredictionsRequest addPredictionsRequest);
}
