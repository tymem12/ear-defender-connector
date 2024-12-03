package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.RestRequestException;
import com.eardefender.model.Analysis;
import com.eardefender.model.File;
import com.eardefender.model.InputParams;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.model.request.BeginProcessingRequest;
import com.eardefender.util.RestRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.eardefender.constants.EarDefenderConstants.*;

@Service
@RequiredArgsConstructor
public class ScraperServiceImpl implements ScraperService {

    private final Logger logger;

    @Value("${scraper.api.server.url}")
    private final String serverUrl;

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
    public void beginScraping(String analysisId, InputParams inputParams) throws AnalysisNotFoundException {
        logger.info("Starting scraping process for analysis ID: {}", analysisId);

        BeginScrapingRequest beginScrapingRequest = BeginScrapingRequest.builder()
                .analysisId(analysisId)
                .inputParams(inputParams)
                .build();

        RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + URL_PATH_BEGIN_SCRAPING,
                beginScrapingRequest,
                request,
                restTemplate,
                logger,
                r -> processSuccessfulScrapingRequest(beginScrapingRequest, r.getStatusCode()),
                r -> processFailedScrapingRequest(beginScrapingRequest, r.getStatusCode()));
    }

    private void processSuccessfulScrapingRequest(BeginScrapingRequest request, HttpStatusCode httpStatusCode) {
        logger.info("Scraping process started successfully for analysis ID: {}. Response status: {}", request.getAnalysisId(), httpStatusCode);
        analysisService.updateStatus(request.getAnalysisId(), STATUS_SCRAPPING);
    }

    private void processFailedScrapingRequest(BeginScrapingRequest request, HttpStatusCode httpStatusCode) {
        logger.error("Failed to start scraping. Response status: {}", httpStatusCode);
        analysisService.finishAnalysis(request.getAnalysisId(), STATUS_ABORTED);
        throw new RestRequestException("Failed to start scraping. Response status: " + httpStatusCode);
    }

    @Override
    public void reportScrapingResults(String analysisId, List<File> files) {
        logger.info("Received scraper request for analysis: {}", analysisId);

        if (files == null || files.isEmpty()) {
            analysisService.finishAnalysis(analysisId, STATUS_FINISHED);
        } else {
            beginProcessing(analysisId,files);
        }
    }

    private void beginProcessing(String analysisId, List<File> fileList) {
        Analysis analysis = analysisService.getAnalysisEnsuringOwnership(analysisId);

        BeginProcessingRequest beginProcessingRequest = BeginProcessingRequest.builder()
                .analysisId(analysis.getId())
                .model(analysis.getInputParams().getModel())
                .files(fileList)
                .build();

        modelService.startProcessing(beginProcessingRequest);
    }
}
