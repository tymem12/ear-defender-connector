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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.eardefender.constants.EarDefenderConstants.*;

@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger logger;

    private final AnalysisRepository analysisRepository;
    private final PredictionResultRepository predictionResultRepository;
    private final ScraperService scraperService;
    private final TimestampService timestampService;
    private final UserService userService;

    @Override
    public void beginAnalysis(BeginAnalysisRequest request) {
        logger.info("Starting new analysis");

        InputParams inputParams = InputParams.builder()
                .maxDepth(request.getDepth())
                .model(request.getModel())
                .maxFiles(request.getMaxFiles())
                .startingPoint(request.getStartingPoint())
                .maxPages(request.getMaxPages())
                .maxTimePerFile(request.getMaxTimePerFile())
                .maxTotalTime(request.getMaxTotalTime())
                .build();

        Analysis analysis = Analysis.builder()
                .owner(userService.getLoggedInUser().getId())
                .timestamp(timestampService.getCurrentTimestampString())
                .status(STATUS_INITIALIZING)
                .inputParams(inputParams).build();

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

        addPredictionsRequest
                .getPredictionResults()
                .forEach(predictionResult -> predictionResult.setTimestamp(timestampService.getCurrentTimestampString()));

        List<PredictionResult> newList = new ArrayList<>(addPredictionsRequest.getPredictionResults());

        if (analysis.getPredictionResults() != null) {
            newList.addAll(analysis.getPredictionResults());
        }

        analysis.setPredictionResults(newList);

        predictionResultRepository.saveAll(newList);

        analysisRepository.save(analysis);

        return analysis;
    }

    @Override
    public Analysis updateStatus(String id, String status) throws AnalysisNotFoundException {
        logger.info("Changing status of analysis {} to {}", id, status);

        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(analysis);

        analysis.setStatus(status);

        analysisRepository.save(analysis);

        return analysis;
    }

    @Override
    public Analysis finishAnalysis(String id, String finalStatus) throws AnalysisNotFoundException {
        logger.info("Finishing analysis {} with {} final status", id, finalStatus);

        Analysis analysis = analysisRepository.findById(id).orElseThrow(() -> new AnalysisNotFoundException(id));

        throwExceptionIfUserIsNotOwner(analysis);

        analysis.setStatus(finalStatus);

        OffsetDateTime startTimestamp = timestampService.getTimestampFromString(analysis.getTimestamp());
        OffsetDateTime finishTimestamp = timestampService.getCurrentTimestamp();
        Duration duration = Duration.between(startTimestamp, finishTimestamp);
        analysis.setDuration(duration.getSeconds());

        analysis.setDeepfakeFileCount(getDeepfakeFileCount(analysis));
        analysis.setFileCount(getFileCount(analysis));

        analysisRepository.save(analysis);

        return analysis;
    }

    private int getFileCount(Analysis analysis) {
        return analysis.getPredictionResults() != null
                ? analysis.getPredictionResults().size()
                : 0;
    }

    private int getDeepfakeFileCount(Analysis analysis) {
        return analysis.getPredictionResults() != null
                ? (int) analysis.getPredictionResults().stream().filter(p -> p.getLabel().equals(LABEL_POSITIVE)).count()
                : 0;
    }

    private void throwExceptionIfUserIsNotOwner(Analysis analysis) {
        User loggedInUser = userService.getLoggedInUser();

        if (analysis.getOwner() == null || !analysis.getOwner().equals(loggedInUser.getId())) {
            throw new UserNotOwnerException(loggedInUser);
        }
    }
}
