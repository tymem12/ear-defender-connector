package com.eardefender.controller;

import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/begin")
    public ResponseEntity<Void> begin(@RequestBody @Valid AnalysisRequest analysisRequest) {
        analysisService.beginAnalysis(analysisRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}