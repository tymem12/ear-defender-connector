package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.RestRequestException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.model.request.BeginProcessingRequest;
import com.eardefender.repository.AnalysisRepository;
import com.eardefender.util.RestRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.eardefender.constants.EarDefenderConstants.*;

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

    @Lazy
    private AnalysisService analysisService;

    @Autowired
    public void setAnalysisService(@Lazy AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    public void beginScraping(BeginScrapingRequest beginScrapingRequest) throws AnalysisNotFoundException {
        logger.info("Starting scraping process for analysis ID: {}", beginScrapingRequest.getAnalysisId());

        RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + URL_PATH_BEGIN_SCRAPING,
                beginScrapingRequest,
                request,
                restTemplate,
                logger,
                r -> {
                    logger.info("Scraping process started successfully for analysis ID: {}", beginScrapingRequest.getAnalysisId());
                    analysisService.updateStatus(beginScrapingRequest.getAnalysisId(), STATUS_SCRAPPING);
                },
                r -> {
                    logger.error("Failed to start scraping. Response status: {}", r.getStatusCode());
                    analysisService.finishAnalysis(beginScrapingRequest.getAnalysisId(), STATUS_ABORTED);
                    throw new RestRequestException("Failed to start scraping. Response status: " + r.getStatusCode());
                });
    }

    @Override
    public void reportScrapingResults(ScraperReportRequest scraperReportRequest) {
        logger.info("Received scraper request for analysis: {}", scraperReportRequest.getAnalysisId());

        Analysis analysis = analysisRepository
                .findById(scraperReportRequest.getAnalysisId())
                .orElseThrow(() -> new AnalysisNotFoundException(scraperReportRequest.getAnalysisId()));

        BeginProcessingRequest startProcessingRequest = new BeginProcessingRequest();
        startProcessingRequest.setModel(analysis.getInputParams().getModel());
        startProcessingRequest.setAnalysisId(scraperReportRequest.getAnalysisId());

        startProcessingRequest.setFiles(scraperReportRequest.getFiles());

        modelService.startProcessing(startProcessingRequest);
    }
}
