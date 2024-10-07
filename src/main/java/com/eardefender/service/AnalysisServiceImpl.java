package com.eardefender.service;

import com.eardefender.model.Analysis;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.eardefender.constants.EarDefenderConstants.STATUS_DOWNLOADING;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    private final AnalysisRepository analysisRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public void beginAnalysis(AnalysisRequest request) {
        Analysis analysis = new Analysis();

        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        analysis.setTimestamp(timestamp);

        analysis.setStatus(STATUS_DOWNLOADING);

        Analysis.InputParams inputParams = new Analysis.InputParams();
        inputParams.setDepth(request.getDepth());
        inputParams.setModel(request.getModel());
        inputParams.setMaxFiles(request.getMaxFiles());
        inputParams.setStartingPoint(request.getStartingPoint());
        analysis.setInputParams(inputParams);

        analysisRepository.save(analysis);
    }
}
