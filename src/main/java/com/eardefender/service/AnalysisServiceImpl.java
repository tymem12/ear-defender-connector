package com.eardefender.service;

import com.eardefender.exception.AnalysisNotFoundException;
import com.eardefender.exception.UserNotOwnerException;
import com.eardefender.mapper.AnalysisMapper;
import com.eardefender.model.Analysis;
import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.User;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;
import com.eardefender.model.request.BeginScrapingRequest;
import com.eardefender.repository.AnalysisRepository;
import com.eardefender.repository.PredictionResultRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.eardefender.constants.EarDefenderConstants.STATUS_DOWNLOADING;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    private final AnalysisRepository analysisRepository;
    private final PredictionResultRepository predictionResultRepository;
    private final ScraperService scraperService;
    private final UserService userService;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, PredictionResultRepository predictionResultRepository, ScraperService scraperService, UserService userService) {
        this.analysisRepository = analysisRepository;
        this.predictionResultRepository = predictionResultRepository;
        this.scraperService = scraperService;
        this.userService = userService;
    }

    @Override
    public void beginAnalysis(BeginAnalysisRequest request) {
        Analysis analysis = new Analysis();

        User owner = userService.getLoggedInUser();
        analysis.setOwner(owner.getId());

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

        BeginScrapingRequest beginScrapingRequest = new BeginScrapingRequest();
        beginScrapingRequest.setAnalysisId(analysis.getId());
        beginScrapingRequest.setInputParams(analysis.getInputParams().clone());

        scraperService.beginScraping(beginScrapingRequest);
    }

    @Override
    public Analysis getById(String id) throws AnalysisNotFoundException {
        Analysis foundAnalysis = analysisRepository
                .findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(foundAnalysis);

        return foundAnalysis;
    }

    @Override
    public List<Analysis> getAll() {
        return analysisRepository.findAnalysisByOwner(userService.getLoggedInUser().getId());
    }

    @Override
    public Analysis update(String id, AnalysisRequest analysisRequest) throws AnalysisNotFoundException {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(analysis);

        Analysis updatedAnalysis = AnalysisMapper.updateFromRequest(analysis, analysisRequest);
        analysisRepository.save(updatedAnalysis);
        return updatedAnalysis;
    }

    @Override
    public void deleteById(String id) throws AnalysisNotFoundException {
        Analysis foundAnalysis = analysisRepository
                .findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(foundAnalysis);

         analysisRepository.deleteById(id);
    }

    @Override
    public Analysis addPredictionResults(String id, AddPredictionsRequest addPredictionsRequest) throws AnalysisNotFoundException {
        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(analysis);

        List<PredictionResult> newList = new ArrayList<>();
        newList.addAll(analysis.getPredictionResults());
        newList.addAll(addPredictionsRequest.getPredictionResults());

        analysis.setPredictionResults(newList);

        predictionResultRepository.saveAll(newList);

        analysisRepository.save(analysis);

        return analysis;
    }

    private void throwExceptionIfUserIsNotOwner(Analysis analysis) {
        User loggedInUser = userService.getLoggedInUser();

        if (analysis.getOwner() == null || !analysis.getOwner().equals(loggedInUser.getId())) {
            throw new UserNotOwnerException(loggedInUser);
        }
    }
}
