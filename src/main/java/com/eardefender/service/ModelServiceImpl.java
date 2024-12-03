package com.eardefender.service;

import com.eardefender.exception.RestRequestException;
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
    public void startProcessing(BeginProcessingRequest startProcessingRequest) {
        logger.info("Starting processing for analysis ID: {}", startProcessingRequest.getAnalysisId());

        RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + URL_PATH_RUN_MODEL,
                startProcessingRequest,
                request,
                restTemplate,
                logger,
                r -> processSuccessfulModelRequest(startProcessingRequest, r.getStatusCode()),
                r -> processFailedModelRequest(startProcessingRequest, r.getStatusCode()));
    }

    private void processSuccessfulModelRequest(BeginProcessingRequest request, HttpStatusCode httpStatusCode) {
        logger.info("Processing started successfully for analysis ID: {}. Response status: {}", request.getAnalysisId(), httpStatusCode);
        analysisService.updateStatus(request.getAnalysisId(), STATUS_PROCESSING);
    }

    private void processFailedModelRequest(BeginProcessingRequest request, HttpStatusCode httpStatusCode) {
        logger.error("Failed to start processing. Response status: {}", httpStatusCode);
        analysisService.finishAnalysis(request.getAnalysisId(), STATUS_ABORTED);
        throw new RestRequestException("Failed to start processing. Response status: " + httpStatusCode);
    }
}
