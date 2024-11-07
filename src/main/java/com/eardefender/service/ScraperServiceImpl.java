package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.RestRequestException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.model.request.StartProcessingRequest;
import com.eardefender.repository.AnalysisRepository;
import com.eardefender.util.RestRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static com.eardefender.constants.EarDefenderConstants.BEGIN_SCRAPING_PATH;

@Service
@RequiredArgsConstructor
public class ScraperServiceImpl implements ScraperService {

    private final Logger logger;

    @Value("${scraper.api.server.url}")
    private final String serverUrl;

    private final AnalysisRepository analysisRepository;
    private final ModelService modelService;
    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Override
    public void beginScraping(BeginScrapingRequest beginScrapingRequest) throws AnalysisNotFoundException {
        logger.info("Starting scraping process for analysis ID: {}", beginScrapingRequest.getAnalysisId());

        ResponseEntity<Void> response = RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + BEGIN_SCRAPING_PATH,
                beginScrapingRequest,
                request,
                restTemplate,
                logger);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Scraping process started successfully for analysis ID: {}", beginScrapingRequest.getAnalysisId());
        } else {
            logger.error("Failed to start scraping. Response status: {}", response.getStatusCode());
            throw new RestRequestException("Failed to start scraping. Response status: " + response.getStatusCode());
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
}
