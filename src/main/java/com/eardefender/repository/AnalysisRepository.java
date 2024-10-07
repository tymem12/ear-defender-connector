package com.eardefender.repository;

import com.eardefender.model.Analysis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnalysisRepository extends MongoRepository<Analysis, String> {

}