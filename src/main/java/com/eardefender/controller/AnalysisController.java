package com.eardefender.controller;

import com.eardefender.mapper.AnalysisMapper;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;
import com.eardefender.model.response.AnalysisResponse;
import com.eardefender.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponse> getById(@PathVariable String id) {
        Analysis analysis = analysisService.getById(id);
        AnalysisResponse analysisResponse = AnalysisMapper.toResponse(analysis);
        return ResponseEntity.status(HttpStatus.OK).body(analysisResponse);
    }

    @GetMapping
    public ResponseEntity<List<AnalysisResponse>> getAll() {
        List<Analysis> analysisList = analysisService.getAll();
        List<AnalysisResponse> analysisResponseList = analysisList
                .stream()
                .map(AnalysisMapper::toResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(analysisResponseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResponse> update(@PathVariable String id, @RequestBody @Valid AnalysisRequest analysisRequest) {
        Analysis result = analysisService.update(id, analysisRequest);

        return ResponseEntity.status(HttpStatus.OK).body(AnalysisMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        analysisService.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/predictions")
    public ResponseEntity<Void> addPredictions(@PathVariable String id, @RequestBody @Valid AddPredictionsRequest addPredictionsRequest) {
        analysisService.addPredictionResults(id, addPredictionsRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping("/begin")
    public ResponseEntity<Void> begin(@RequestBody @Valid BeginAnalysisRequest beginAnalysisRequest) {
        analysisService.beginAnalysis(beginAnalysisRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}