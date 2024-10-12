package com.eardefender.service;

import com.eardefender.model.Analysis;
import com.eardefender.model.PredictionResult;

import java.util.List;

public interface PredictionResultService {
    void deleteByLinkAndModel(String link, String model);
    void deleteAll();
    void deleteByModel(String model);
    List<PredictionResult> getAll();
    List<PredictionResult> findByModel(String model);
    List<PredictionResult> findByLinksAndModel(List<String> link, String model);
    PredictionResult create(PredictionResult predictionResult);
}
