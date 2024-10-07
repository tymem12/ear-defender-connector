package com.eardefender.service;

import com.eardefender.model.Analysis;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.repository.AnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalysisServiceImplTest {
    @Mock
    private AnalysisRepository analysisRepository;

    @InjectMocks
    private AnalysisServiceImpl analysisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void beginAnalysis_savesAnalysisToRepository() {
        AnalysisRequest request = new AnalysisRequest();
        request.setDepth(3);
        request.setModel("model-example");
        request.setMaxFiles(100);
        request.setStartingPoint("http://example.com");

        analysisService.beginAnalysis(request);

        ArgumentCaptor<Analysis> analysisCaptor = ArgumentCaptor.forClass(Analysis.class);
        verify(analysisRepository, times(1)).save(analysisCaptor.capture());

        Analysis capturedAnalysis = analysisCaptor.getValue();

        assertEquals("model-example", capturedAnalysis.getInputParams().getModel());
        assertEquals(3, capturedAnalysis.getInputParams().getDepth());
        assertEquals(100, capturedAnalysis.getInputParams().getMaxFiles());
        assertEquals("http://example.com", capturedAnalysis.getInputParams().getStartingPoint());

        assertEquals("DOWNLOADING", capturedAnalysis.getStatus());
        assertNotNull(capturedAnalysis.getTimestamp());
    }
}