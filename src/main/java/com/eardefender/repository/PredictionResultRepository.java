package com.eardefender.repository;

import com.eardefender.model.PredictionResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PredictionResultRepository extends MongoRepository<PredictionResult, String> {

    List<PredictionResult> findByModel(String model);

    List<PredictionResult> findByLinkInAndModel(List<String> link, String model);
}