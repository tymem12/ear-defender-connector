package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.RestRequestException;
import com.eardefender.model.Analysis;
import com.eardefender.model.InputParams;
import com.eardefender.model.request.ScraperReportRequest;
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

import static com.eardefender.constants.EarDefenderConstants.STATUS_ABORTED;
import static com.eardefender.constants.EarDefenderConstants.STATUS_SCRAPPING;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScraperServiceImplTest {
    @Mock
    private AnalysisService analysisService;
    @Mock
    private ModelService modelService;
    @Mock
    private Logger logger;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ScraperServiceImpl scraperService;

    private Analysis analysis;
    private ScraperReportRequest scraperReportRequest;

    @BeforeEach
    void setUp() throws IOException {
        setUpAnalysisModel();
        setUpScraperReportRequest();

        MockitoAnnotations.openMocks(this);

        scraperService = new ScraperServiceImpl(logger, "http://mock-server", modelService, restTemplate, request);
        scraperService.setAnalysisService(analysisService);
    }

    @Test
    void reportScrapingResults_AnalysisInRepository_CallsModelService() {
        when(analysisService.getAnalysisEnsuringOwnership(anyString())).thenReturn(analysis);

        scraperService.reportScrapingResults(scraperReportRequest.getAnalysisId(), scraperReportRequest.getFiles());

        verify(analysisService, times(1)).getAnalysisEnsuringOwnership(anyString());
        verify(modelService, times(1)).startProcessing(any());
    }

    @Test
    void reportScrapingResults_AnalysisNotInRepository_ThrowsException() {
        when(analysisService.getAnalysisEnsuringOwnership(anyString())).thenThrow(new AnalysisNotFoundException("id"));

        assertThrows(AnalysisNotFoundException.class, () -> scraperService.reportScrapingResults(scraperReportRequest.getAnalysisId(), scraperReportRequest.getFiles()));
    }

    @Test
    void beginScraping_SuccessfulRequest_continuesAnalysis() {
        ResponseEntity<Void> response = ResponseEntity.ok().build();

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        scraperService.beginScraping("id", new InputParams());

        verify(analysisService, times(1)).updateStatus("id", STATUS_SCRAPPING);
    }

    @Test
    void beginScraping_FailedRequest_abortsAnalysis() {
        ResponseEntity<Void> response = ResponseEntity.internalServerError().build();

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class))).thenReturn(response);

        assertThrows(RestRequestException.class, () -> scraperService.beginScraping("id", new InputParams()));

        verify(analysisService, times(1)).finishAnalysis("id", STATUS_ABORTED);
    }

    private void setUpAnalysisModel() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/analysis.json");
        analysis = objectMapper.readValue(jsonInputStream, Analysis.class);
    }

    private void setUpScraperReportRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/jsons/scraper_report_request.json");
        scraperReportRequest = objectMapper.readValue(jsonInputStream, ScraperReportRequest.class);
    }
}