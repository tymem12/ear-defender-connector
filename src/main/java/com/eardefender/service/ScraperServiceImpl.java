package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.model.request.StartProcessingRequest;
import com.eardefender.repository.AnalysisRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static com.eardefender.constants.EarDefenderConstants.BEGIN_SCRAPING_PATH;
import static org.springframework.http.HttpMethod.POST;

@Service
public class ScraperServiceImpl implements ScraperService {
    private final Logger logger;

    private final AnalysisRepository analysisRepository;
    private final ModelService modelService;
    private final RestTemplate restTemplate;
    private final String serverUrl;
    private final HttpServletRequest request;

    public ScraperServiceImpl(
            Logger logger,
            AnalysisRepository analysisRepository,
            ModelService modelService,
            RestTemplate restTemplate,
            @Value("${scraper.api.server.url}") String serverUrl, HttpServletRequest request) {
        this.logger = logger;
        this.analysisRepository = analysisRepository;
        this.modelService = modelService;
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.request = request;
    }

    @Override
    public void beginScraping(BeginScrapingRequest beginScrapingRequest) throws AnalysisNotFoundException {
        String url = serverUrl + BEGIN_SCRAPING_PATH;
        logger.info("Starting scraping process for analysis ID: {}", beginScrapingRequest.getAnalysisId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getToken(request));

        HttpEntity<BeginScrapingRequest> requestEntity = new HttpEntity<>(beginScrapingRequest, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                url,
                POST,
                requestEntity,
                Void.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Scraping process started successfully for analysis ID: {}", beginScrapingRequest.getAnalysisId());
        } else {
            logger.error("Failed to start scraping. Response status: {}", response.getStatusCode());
            throw new RuntimeException("Failed to start scraping. Response status: " + response.getStatusCode());
        }
    }

    @Override
    public void reportScrapingResults(ScraperReportRequest scraperReportRequest) {
        logger.info("Received scraper request for analysis: {}", scraperReportRequest.getAnalysisId());

        Analysis analysis = analysisRepository
                .findById(scraperReportRequest.getAnalysisId())
                .orElseThrow(() -> new AnalysisNotFoundException(scraperReportRequest.getAnalysisId()));

        StartProcessingRequest startProcessingRequest = new StartProcessingRequest();
        startProcessingRequest.setModel(analysis.getInputParams().getModel());
        startProcessingRequest.setAnalysisId(scraperReportRequest.getAnalysisId());
        startProcessingRequest.setFilePaths(new ArrayList<>(scraperReportRequest.getNewFilePaths()));

        modelService.startProcessing(startProcessingRequest);
    }

    private String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
