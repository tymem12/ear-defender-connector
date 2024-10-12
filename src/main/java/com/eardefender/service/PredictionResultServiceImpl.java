package com.eardefender.service;

import com.eardefender.exception.PredictionResultNotFoundException;
import com.eardefender.model.PredictionResult;
import com.eardefender.repository.PredictionResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionResultServiceImpl implements PredictionResultService {
    private final PredictionResultRepository predictionResultRepository;

    public PredictionResultServiceImpl(PredictionResultRepository predictionResultRepository) {
        this.predictionResultRepository = predictionResultRepository;
    }


    @Override
    public void deleteByLinkAndModel(String link, String model) {
        predictionResultRepository.deleteByLinkAndModel(link, model);
    }

    @Override
    public void deleteAll() {
        predictionResultRepository.deleteAll();
    }

    @Override
    public void deleteByModel(String model) {
        predictionResultRepository.deleteByModel(model);
    }

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
    public PredictionResult findByLinkAndModel(String link, String model) {
        return predictionResultRepository.findByLinkAndModel(link, model).orElseThrow(() -> new PredictionResultNotFoundException(link, model));
    }

    @Override
    public PredictionResult create(PredictionResult predictionResult) {
        return predictionResultRepository.save(predictionResult);
    }
}
