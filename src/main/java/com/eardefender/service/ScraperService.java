package com.eardefender.service;

import com.eardefender.model.File;
import com.eardefender.model.InputParams;

import java.util.List;

public interface ScraperService {

    void beginScraping(String analysisId, InputParams inputParams);

    void reportScrapingResults(String analysisId, List<File> files);
}
