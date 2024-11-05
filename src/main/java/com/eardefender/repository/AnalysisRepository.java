package com.eardefender.repository;

import com.eardefender.model.Analysis;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnalysisRepository extends MongoRepository<Analysis, String> {

    List<Analysis> findAnalysisByOwner(String ownerId);
}