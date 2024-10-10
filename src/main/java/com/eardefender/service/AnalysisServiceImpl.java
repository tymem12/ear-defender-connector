package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.mapper.AnalysisMapper;
import com.eardefender.model.Analysis;
import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;
import com.eardefender.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.eardefender.constants.EarDefenderConstants.STATUS_DOWNLOADING;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    private final AnalysisRepository analysisRepository;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    @Override
    public void beginAnalysis(BeginAnalysisRequest request) {
        Analysis analysis = new Analysis();

        String timestamp = OffsetDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        analysis.setTimestamp(timestamp);

        analysis.setStatus(STATUS_DOWNLOADING);

        InputParams inputParams = new InputParams();
        inputParams.setDepth(request.getDepth());
        inputParams.setModel(request.getModel());
        inputParams.setMaxFiles(request.getMaxFiles());
        inputParams.setStartingPoint(request.getStartingPoint());
        analysis.setInputParams(inputParams);

        analysisRepository.save(analysis);
    }

    @Override
    public Analysis getById(String id) {
        return analysisRepository
                .findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));
    }

    @Override
    public List<Analysis> getAll() {
        return analysisRepository.findAll();
    }

    @Override
    public Analysis update(String id, AnalysisRequest analysisRequest) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));
        Analysis updatedAnalysis = AnalysisMapper.updateFromRequest(analysis, analysisRequest);
        analysisRepository.save(updatedAnalysis);
        return updatedAnalysis;
    }

    @Override
    public void deleteById(String id) {
         analysisRepository.deleteById(id);
    }

    @Override
    public Analysis addPredictionResults(String id, AddPredictionsRequest addPredictionsRequest) {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));

        List<PredictionResult> newList = new ArrayList<>();
        newList.addAll(analysis.getPredictionResults());
        newList.addAll(addPredictionsRequest.getPredictionResults());

        analysis.setPredictionResults(newList);

        analysisRepository.save(analysis);

        return analysis;
    }
}
