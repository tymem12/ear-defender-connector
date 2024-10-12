package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.model.request.StartProcessingRequest;
import com.eardefender.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScraperServiceImpl implements ScraperService {
    private final AnalysisRepository analysisRepository;
    private final ModelService modelService;

    public ScraperServiceImpl(AnalysisRepository analysisRepository, ModelService modelService) {
        this.analysisRepository = analysisRepository;
        this.modelService = modelService;
    }

    @Override
    public void beginScraping(BeginScrapingRequest beginScrapingRequest) throws AnalysisNotFoundException {
        //TODO: Call Scraper API here
    }

    @Override
    public void reportScrapingResults(ScraperReportRequest scraperReportRequest) {
        Analysis analysis = analysisRepository
                .findById(scraperReportRequest.getAnalysisId())
                .orElseThrow(() -> new AnalysisNotFoundException(scraperReportRequest.getAnalysisId()));

        StartProcessingRequest startProcessingRequest = new StartProcessingRequest();
        startProcessingRequest.setModel(analysis.getInputParams().getModel());
        startProcessingRequest.setAnalysisId(scraperReportRequest.getAnalysisId());
        startProcessingRequest.setNewFilePaths(new ArrayList<>(scraperReportRequest.getNewFilePaths()));

        modelService.startProcessing(startProcessingRequest);
    }
}
