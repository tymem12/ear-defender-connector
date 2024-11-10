package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.UserNotOwnerException;
import com.eardefender.model.Analysis;
import com.eardefender.model.User;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;
import com.eardefender.repository.AnalysisRepository;
import com.eardefender.repository.PredictionResultRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.eardefender.constants.EarDefenderConstants.STATUS_INITIALIZING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalysisServiceImplTest {
    @Mock
    private AnalysisRepository analysisRepository;
    @Mock
    private PredictionResultRepository predictionResultRepository;
    @Mock
    private ScraperService scraperService;
    @Mock
    private UserService userService;
    @Mock
    private TimestampService timestampService;
    @Mock
    private Logger logger;

    @InjectMocks
    private AnalysisServiceImpl analysisService;

    private Analysis analysisModel;
    private BeginAnalysisRequest beginAnalysisRequest;
    private AnalysisRequest analysisRequest;
    private AddPredictionsRequest addPredictionsRequest;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() throws IOException {
        setUpModel();
        setUpBeginAnalysisRequest();
        setUpAnalysisRequest();
        setUpAddPredictionsRequest();
        setUpUsers();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void beginAnalysis_SavesAnalysisAndCallsModelService() {
        doNothing().when(scraperService).beginScraping(any());
        when(userService.getLoggedInUser()).thenReturn(user1);
        when(timestampService.getCurrentTimestampString()).thenReturn("timestamp");

        analysisService.beginAnalysis(beginAnalysisRequest);

        ArgumentCaptor<Analysis> analysisCaptor = ArgumentCaptor.forClass(Analysis.class);
        verify(analysisRepository, times(1)).save(analysisCaptor.capture());

        Analysis capturedAnalysis = analysisCaptor.getValue();

        assertEquals(beginAnalysisRequest.getModel(), capturedAnalysis.getInputParams().getModel());
        assertEquals(beginAnalysisRequest.getDepth(), capturedAnalysis.getInputParams().getMaxDepth());
        assertEquals(beginAnalysisRequest.getMaxFiles(), capturedAnalysis.getInputParams().getMaxFiles());
        assertEquals(beginAnalysisRequest.getStartingPoint(), capturedAnalysis.getInputParams().getStartingPoint());

        assertEquals(STATUS_INITIALIZING, capturedAnalysis.getStatus());
        assertEquals("timestamp", capturedAnalysis.getTimestamp());

        assertEquals(user1.getId(), capturedAnalysis.getOwner());

        verify(scraperService, times(1)).beginScraping(any());
    }

    @Test
    void getById_AnalysisInRepository_ReturnsAnalysis() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(userService.getLoggedInUser()).thenReturn(user1);

        Analysis analysis = analysisService.getById("testId");

        assertEquals(analysisModel, analysis);
    }

    @Test
    void getById_AnalysisInRepositoryUserNorOwner_ReturnsAnalysis() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(userService.getLoggedInUser()).thenReturn(user2);

        assertThrows(UserNotOwnerException.class, () -> analysisService.getById("testId"));
    }

    @Test
    void getById_AnalysisInRepository_ThrowsAnalysisNotFoundException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> analysisService.getById("testId"));
    }

    @Test
    void getAll_AnalysisInRepository_ReturnsAnalyses() {
        when(analysisRepository.findAnalysisByOwner(any())).thenReturn(List.of(analysisModel));
        when(userService.getLoggedInUser()).thenReturn(user1);

        List<Analysis> result = analysisService.getAll();

        assertEquals(1, result.size());
        assertEquals(analysisModel, result.get(0));
    }

    @Test
    void getAll_EmptyRepository_ReturnsEmptyList() {
        when(analysisRepository.findAnalysisByOwner(any())).thenReturn(List.of());
        when(userService.getLoggedInUser()).thenReturn(user1);

        List<Analysis> result = analysisService.getAll();

        assertEquals(0, result.size());
    }

    @Test
    void deleteById_AnalysisInRepository_DeletesAnalysis() {
        when(userService.getLoggedInUser()).thenReturn(user1);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        analysisService.deleteById(analysisModel.getId());

        verify(analysisRepository, times(1)).deleteById(analysisModel.getId());
    }

    @Test
    void deleteById_AnalysisInRepositoryUserNotOwner_ThrowsException() {
        when(userService.getLoggedInUser()).thenReturn(user2);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        assertThrows(UserNotOwnerException.class, () -> analysisService.deleteById(analysisModel.getId()));
}

    @Test
    void update_AnalysisInRepository_AnalysisIsUpdated() {
        when(userService.getLoggedInUser()).thenReturn(user1);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        Analysis result = analysisService.update(analysisModel.getId(), analysisRequest);

        assertEquals(analysisRequest.getPredictionResults(), result.getPredictionResults());
        assertTrue(result.getDuration() > 0);
        assertEquals(analysisRequest.getPredictionResults().size(), result.getFileCount());

        verify(analysisRepository, times(1)).save(any());
    }

    @Test
    void update_AnalysisInRepositoryUserNotOwner_ThrowsException() {
        when(userService.getLoggedInUser()).thenReturn(user2);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        assertThrows(UserNotOwnerException.class, () -> analysisService.update(analysisModel.getId(), analysisRequest));
    }

    @Test
    void update_AnalysisNotInRepository_ThrowsException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> analysisService.update(analysisModel.getId(), analysisRequest));
    }

    @Test
    void addPredictions_AnalysisInRepository_AddsPredictions() {
        when(userService.getLoggedInUser()).thenReturn(user1);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(predictionResultRepository.saveAll(any())).thenReturn(List.of());

        Analysis result = analysisService.addPredictionResults(analysisModel.getId(), addPredictionsRequest);

        assertEquals(5, result.getPredictionResults().size());
        assertTrue(result.getPredictionResults().containsAll(addPredictionsRequest.getPredictionResults()));

        verify(analysisRepository, times(1)).save(any());
    }

    @Test
    void addPredictions_AnalysisNotInRepository_ThrowsException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> analysisService.addPredictionResults(analysisModel.getId(), addPredictionsRequest));
    }

    @Test
    void addPredictions_AnalysisInRepositoryUserNotOwner_ThrowsException() {
        when(userService.getLoggedInUser()).thenReturn(user2);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(predictionResultRepository.saveAll(any())).thenReturn(List.of());

        assertThrows(UserNotOwnerException.class, () -> analysisService.addPredictionResults(analysisModel.getId(), addPredictionsRequest));
    }

    @Test
    void updateStatus_AnalysisInRepository_StatusIsUpdated() {
        when(userService.getLoggedInUser()).thenReturn(user1);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        Analysis result = analysisService.updateStatus(analysisModel.getId(), "new");

        assertEquals(result.getStatus(), "new");
    }

    @Test
    void updateStatus_AnalysisNotInRepository_ThrowsException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> analysisService.updateStatus(analysisModel.getId(), "new"));
    }

    @Test
    void updateStatus_AnalysisNotInRepositoryUserNotOwner_ThrowsException() {
        when(userService.getLoggedInUser()).thenReturn(user2);
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));

        assertThrows(UserNotOwnerException.class, () -> analysisService.updateStatus(analysisModel.getId(), "new"));
    }

    @Test
    void finishAnalysis_AnalysisInRepository_AnalysisIsFinished() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(timestampService.getCurrentTimestamp())
                .thenReturn(OffsetDateTime.parse("1997-07-16T19:21:30+01:00"));
        when(timestampService.getTimestampFromString(any()))
                .thenReturn(OffsetDateTime.parse("1997-07-16T19:20:30+01:00"));
        when(userService.getLoggedInUser()).thenReturn(user1);

        Analysis result = analysisService.finishAnalysis(analysisModel.getId(), "finish");

        assertEquals(result.getStatus(), "finish");
        assertEquals(result.getDuration(), 60);
    }

    @Test
    void finishAnalysis_AnalysisNotInRepository_ThrowsException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> analysisService.finishAnalysis(analysisModel.getId(), "finish"));
    }

    @Test
    void finishAnalysis_AnalysisInRepositoryUserNotOwner_AnalysisIsFinished() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysisModel));
        when(userService.getLoggedInUser()).thenReturn(user2);

        assertThrows(UserNotOwnerException.class, () -> analysisService.finishAnalysis(analysisModel.getId(), "finish"));
    }

    private void setUpModel() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/analysis.json");
        analysisModel = objectMapper.readValue(jsonInputStream, Analysis.class);
    }

    private void setUpBeginAnalysisRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/begin_analysis_request.json");
        beginAnalysisRequest = objectMapper.readValue(jsonInputStream, BeginAnalysisRequest.class);
    }

    private void setUpAnalysisRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/analysis_request.json");
        analysisRequest = objectMapper.readValue(jsonInputStream, AnalysisRequest.class);
    }

    private void setUpAddPredictionsRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/add_predictions_request.json");
        addPredictionsRequest = objectMapper.readValue(jsonInputStream, AddPredictionsRequest.class);
    }

    private void setUpUsers() {
        user1 = User.builder()
                .email("dezo505@gmail.com")
                .id("ID-1")
                .build();
        user2 = User.builder()
                .email("Arbuz01")
                .id("ID-2")
                .build();
    }
}