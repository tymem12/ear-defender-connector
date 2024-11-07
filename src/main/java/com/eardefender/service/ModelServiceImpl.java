package com.eardefender.service;

import com.eardefender.exception.RestRequestException;
import com.eardefender.model.request.StartProcessingRequest;
import com.eardefender.util.RestRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.eardefender.constants.EarDefenderConstants.RUN_MODEL_PATH;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final Logger logger;

    @Value("${model.api.server.url}")
    private final String serverUrl;

    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Override
    public void startProcessing(StartProcessingRequest startProcessingRequest) {
        logger.info("Starting processing for analysis ID: {}", startProcessingRequest.getAnalysisId());

        ResponseEntity<Void> response = RestRequestUtil.sendPostRequestWithAuth(
                serverUrl + RUN_MODEL_PATH,
                startProcessingRequest,
                request,
                restTemplate,
                logger);

        if (response.getStatusCode().is2xxSuccessful()) {
            logger.info("Processing started successfully for analysis ID: {}", startProcessingRequest.getAnalysisId());
        } else {
            logger.error("Failed to start processing. Response status: {}", response.getStatusCode());
            throw new RestRequestException("Failed to start processing. Response status: " + response.getStatusCode());
        }
    }
}
