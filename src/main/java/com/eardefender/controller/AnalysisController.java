package com.eardefender.controller;

import com.eardefender.mapper.AnalysisMapper;
import com.eardefender.model.Analysis;
import com.eardefender.model.request.AddPredictionsRequest;
import com.eardefender.model.request.AnalysisRequest;
import com.eardefender.model.request.BeginAnalysisRequest;
import com.eardefender.model.response.AnalysisResponse;
import com.eardefender.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.eardefender.constants.EarDefenderConstants.STATUS_FINISHED;

@Validated
@RestController
@RequestMapping("/analyses")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @Operation(summary = "Get an analysis by ID",
            description = "Returns the analysis details for the provided analysis ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analysis found"),
            @ApiResponse(responseCode = "404", description = "Analysis not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponse> getById(@Parameter(description = "ID of the analysis to be retrieved", required = true) @PathVariable String id) {
        Analysis analysis = analysisService.getById(id);
        AnalysisResponse analysisResponse = AnalysisMapper.toDetailedResponse(analysis);
        return ResponseEntity.status(HttpStatus.OK).body(analysisResponse);
    }

    @Operation(summary = "Get all analyses",
            description = "Returns a list of all analyses.")
    @ApiResponse(responseCode = "200", description = "List of analyses returned successfully.")
    @GetMapping
    public ResponseEntity<List<AnalysisResponse>> getAll() {
        List<Analysis> analysisList = analysisService.getAll();
        List<AnalysisResponse> analysisResponseList = analysisList
                .stream()
                .map(AnalysisMapper::toBaseResponse)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(analysisResponseList);
    }

    @Operation(summary = "Update analysis with given ID",
            description = "Updates the details of the analysis specified by the ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analysis updated successfully"),
            @ApiResponse(responseCode = "404", description = "Analysis not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResponse> update(
            @Parameter(description = "ID of the analysis to be updated", required = true) @PathVariable String id,
            @RequestBody @Valid AnalysisRequest analysisRequest) {
        Analysis result = analysisService.update(id, analysisRequest);
        return ResponseEntity.status(HttpStatus.OK).body(AnalysisMapper.toDetailedResponse(result));
    }

    @Operation(summary = "Delete analysis by ID",
            description = "Deletes the analysis specified by the ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analysis deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Analysis not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID of the analysis to be deleted", required = true) @PathVariable String id) {
        analysisService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Add predictions to an analysis",
            description = "Adds prediction results to the analysis specified by the ID. It doesn't override existing prediction results. Also saves predictions to prediction collection.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Predictions added successfully"),
            @ApiResponse(responseCode = "404", description = "Analysis not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PutMapping("/{id}/predictions")
    public ResponseEntity<Void> addPredictions(
            @Parameter(description = "ID of the analysis to which predictions are added", required = true) @PathVariable String id,
            @RequestBody @Valid AddPredictionsRequest addPredictionsRequest) {
        analysisService.addPredictionResults(id, addPredictionsRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Begin analysis",
            description = "Starts a new analysis process based on the provided request.")
    @ApiResponse(responseCode = "200", description = "Analysis process started successfully")
    @PostMapping("/begin")
    public ResponseEntity<Void> begin(@RequestBody @Valid BeginAnalysisRequest beginAnalysisRequest) {
        analysisService.beginAnalysis(beginAnalysisRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Finish Analysis",
            description = "Finishes Analysis.")
    @ApiResponse(responseCode = "200", description = "Analysis finished successfully.")
    @PostMapping("/{id}/finish")
    public ResponseEntity<Void> finishAnalysis(@PathVariable String id) {
        analysisService.finishAnalysis(id, STATUS_FINISHED);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Abort Analysis",
            description = "Aborts Analysis.")
    @ApiResponse(responseCode = "200", description = "Analysis aborted successfully.")
    @PostMapping("/{id}/abort")
    public ResponseEntity<Void> abortAnalysis(@PathVariable String id) {
        analysisService.finishAnalysis(id, STATUS_FINISHED);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
