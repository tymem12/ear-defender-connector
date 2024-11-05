package com.eardefender.service;

import com.eardefender.model.PredictionResult;
import com.eardefender.repository.PredictionResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionResultServiceImpl implements PredictionResultService {

    private final PredictionResultRepository predictionResultRepository;

    @Override
    public List<PredictionResult> getAll() {
        return predictionResultRepository.findAll();
    }

    @Override
    public List<PredictionResult> findByModel(String model) {
        return predictionResultRepository.findByModel(model);
    }

    @Override
    public List<PredictionResult> findByLinksAndModel(List<String> link, String model) {
        return predictionResultRepository.findByLinkInAndModel(link, model);
    }

    @Override
    public PredictionResult create(PredictionResult predictionResult) {
        return predictionResultRepository.save(predictionResult);
    }
}
