package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.repository.AnalysisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ScraperServiceImplTest {
    @Mock
    private AnalysisRepository analysisRepository;
    @Mock
    private ModelService modelService;
    @Mock
    private Logger logger;

    @InjectMocks
    private ScraperServiceImpl scraperService;

    private Analysis analysis;
    private ScraperReportRequest scraperReportRequest;

    @BeforeEach
    void setUp() throws IOException {
        setUpAnalysisModel();
        setUpScraperReportRequest();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void reportScrapingResults_AnalysisInRepository_CallsModelService() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.of(analysis));

        scraperService.reportScrapingResults(scraperReportRequest);

        verify(analysisRepository, times(1)).findById(anyString());
        verify(modelService, times(1)).startProcessing(any());
    }

    @Test
    void reportScrapingResults_AnalysisNotInRepository_ThrowsException() {
        when(analysisRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(AnalysisNotFoundException.class, () -> scraperService.reportScrapingResults(scraperReportRequest));
    }

    private void setUpAnalysisModel() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/analysis.json");
        analysis = objectMapper.readValue(jsonInputStream, Analysis.class);
    }

    private void setUpScraperReportRequest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        InputStream jsonInputStream = getClass().getResourceAsStream("/scraper_report_request.json");
        scraperReportRequest = objectMapper.readValue(jsonInputStream, ScraperReportRequest.class);
    }
}