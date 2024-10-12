package com.eardefender.service;

import com.eardefender.exception.PredictionResultNotFoundException;
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
import java.util.Optional;

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
    void deleteByLinkAndModel_deletesPredictionResult() {
        doNothing().when(predictionResultRepository).deleteByLinkAndModel(anyString(), anyString());

        predictionResultService.deleteByLinkAndModel(predictionResult.getLink(), predictionResult.getModel());

        verify(predictionResultRepository, times(1)).deleteByLinkAndModel(predictionResultRequest.getLink(), predictionResultRequest.getModel());
    }

    @Test
    void deleteAll_deletesPredictionResult() {
        doNothing().when(predictionResultRepository).deleteAll();

        predictionResultService.deleteAll();

        verify(predictionResultRepository, times(1)).deleteAll();
    }

    @Test
    void deleteByModel_deletesPredictionResult() {
        doNothing().when(predictionResultRepository).deleteByModel(anyString());

        predictionResultService.deleteByModel(predictionResult.getModel());

        verify(predictionResultRepository, times(1)).deleteByModel(predictionResultRequest.getModel());
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
    void findByLinkAndModel_PredictionResultInRepository_ReturnsPredictionResult() {
        when(predictionResultRepository.findByLinkAndModel(anyString(), anyString())).thenReturn(Optional.of(predictionResult));

        PredictionResult result = predictionResultService.findByLinkAndModel(predictionResult.getLink(), predictionResult.getModel());

        assertNotNull(result);
        assertEquals(predictionResult, result);

        verify(predictionResultRepository, times(1)).findByLinkAndModel(predictionResult.getLink(), predictionResult.getModel());
    }

    @Test
    void findByLinkAndModel_PredictionResultNotInRepository_ReturnsPredictionResult() {
        when(predictionResultRepository.findByLinkAndModel(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(PredictionResultNotFoundException.class, () -> predictionResultService.findByLinkAndModel(predictionResult.getLink(), predictionResult.getModel()));
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