package com.eardefender.service;

import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.PredictionResultRequest;
import com.eardefender.repository.PredictionResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PredictionResultServiceImplTest {
    @Mock
    private PredictionResultRepository predictionResultRepository;
    @InjectMocks
    private PredictionResultServiceImpl predictionResultService;

    private PredictionResult predictionResult;
    private PredictionResultRequest predictionResultRequest;

    @BeforeEach
    void setUp() throws IOException {
        setupPredictionResult();
        setupPredictionResultRequest();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll_ReturnsAll() {
        when(predictionResultRepository.findAll()).thenReturn(List.of(predictionResult));

        List<PredictionResult> predictionResults = predictionResultService.getAll();

        assertNotNull(predictionResults);
        assertEquals(1, predictionResults.size());

        verify(predictionResultRepository, times(1)).findAll();
    }

    @Test
    void findByModel_ReturnsPredictionResult() {
        when(predictionResultRepository.findByModel(anyString())).thenReturn(List.of(predictionResult));

        List<PredictionResult> predictionResults = predictionResultService.findByModel(predictionResult.getModel());

        assertNotNull(predictionResults);
        assertEquals(1, predictionResults.size());

        verify(predictionResultRepository, times(1)).findByModel(predictionResultRequest.getModel());
    }

    @Test
    void findByLinksAndModel_ReturnsPredictionResult() {
        when(predictionResultRepository.findByLinkInAndModel(anyList(), anyString())).thenReturn(List.of(predictionResult));

        List<PredictionResult> predictionResults = predictionResultService.findByLinksAndModel(List.of(predictionResult.getLink()), predictionResult.getModel());

        assertNotNull(predictionResults);
        assertEquals(1, predictionResults.size());

        verify(predictionResultRepository, times(1)).findByLinkInAndModel(List.of(predictionResult.getLink()), predictionResult.getModel());
    }

    @Test
    void create() {
        when(predictionResultRepository.save(any())).thenReturn(predictionResult);

        PredictionResult result = predictionResultService.create(predictionResult);

        assertNotNull(result);

        verify(predictionResultRepository, times(1)).save(any());
    }

    private void setupPredictionResultRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/prediction_result_request.json");
        predictionResultRequest = objectMapper.readValue(jsonInputStream, PredictionResultRequest.class);
    }

    private void setupPredictionResult() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/prediction_result.json");
        predictionResult = objectMapper.readValue(jsonInputStream, PredictionResult.class);
    }
}