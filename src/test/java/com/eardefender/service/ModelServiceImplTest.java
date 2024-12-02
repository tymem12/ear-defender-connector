package com.eardefender.service;

import com.eardefender.exception.RestRequestException;
import com.eardefender.model.request.BeginProcessingRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

import static com.eardefender.constants.EarDefenderConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ModelServiceImplTest {
    @Mock
    private AnalysisService analysisService;
    @Mock
    private Logger logger;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ModelServiceImpl modelService;

    private BeginProcessingRequest beginProcessingRequest;

    @BeforeEach
    void setUp() throws IOException {
        setUpBeginProcessingRequest();

        MockitoAnnotations.openMocks(this);

        modelService = new ModelServiceImpl(logger, "http://mock-server", restTemplate, request);
        modelService.setAnalysisService(analysisService);
    }

    @Test
    void beginProcessing_SuccessfulRequest_continuesAnalysis() {
        ResponseEntity<Void> response = ResponseEntity.ok().build();

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        modelService.startProcessing(beginProcessingRequest);

        verify(analysisService, times(1)).updateStatus("id", STATUS_PROCESSING);
    }

    @Test
    void beginProcessing_FailedRequest_abortsAnalysis() {
        ResponseEntity<Void> response = ResponseEntity.internalServerError().build();

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        assertThrows(RestRequestException.class, () -> modelService.startProcessing(beginProcessingRequest));

        verify(analysisService, times(1)).finishAnalysis("id", STATUS_ABORTED);
    }


    private void setUpBeginProcessingRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/begin_processing_request.json");
        beginProcessingRequest = objectMapper.readValue(jsonInputStream, BeginProcessingRequest.class);
    }
}