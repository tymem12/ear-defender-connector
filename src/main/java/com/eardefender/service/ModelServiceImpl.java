package com.eardefender.service;

import com.eardefender.exception.RestRequestException;
import com.eardefender.model.request.StartProcessingRequest;
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
public class ModelServiceImpl implements ModelService {

    private final Logger logger;

    @Value("${model.api.server.url}")
    private final String serverUrl;

    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Lazy
    private AnalysisService analysisService;

    @Autowired
    public void setAnalysisService(@Lazy AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    public void startProcessing(StartProcessingRequest startProcessingRequest) {
        logger.info("Starting processing for analysis ID: {}", startProcessingRequest.getAnalysisId());

        RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + URL_PATH_RUN_MODEL,
                startProcessingRequest,
                request,
                restTemplate,
                logger,
                r -> {
                    logger.info("Processing started successfully for analysis ID: {}", startProcessingRequest.getAnalysisId());
                    analysisService.updateStatus(startProcessingRequest.getAnalysisId(), STATUS_PROCESSING);
                },
                r -> {
                    logger.error("Failed to start processing. Response status: {}", r.getStatusCode());
                    analysisService.finishAnalysis(startProcessingRequest.getAnalysisId(), STATUS_ABORTED);
                    throw new RestRequestException("Failed to start processing. Response status: " + r.getStatusCode());
                });
    }
}
