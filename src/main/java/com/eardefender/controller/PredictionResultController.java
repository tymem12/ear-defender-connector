package com.eardefender.controller;

import com.eardefender.mapper.PredictionResultMapper;
import com.eardefender.model.PredictionResult;
import com.eardefender.model.request.GetPredictionsRequest;
import com.eardefender.model.request.PredictionResultRequest;
import com.eardefender.model.response.PredictionResultResponse;
import com.eardefender.service.PredictionResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/predictions")
public class PredictionResultController {

    private final PredictionResultService predictionResultService;

    public PredictionResultController(PredictionResultService predictionResultService) {
        this.predictionResultService = predictionResultService;
    }

    @Operation(summary = "Get all prediction results",
            description = "Returns a list of all prediction results.")
    @ApiResponse(responseCode = "200", description = "List of prediction results returned successfully.")
    @GetMapping
    public ResponseEntity<List<PredictionResultResponse>> getAll() {
        List<PredictionResult> predictionResults = predictionResultService.getAll();
        List<PredictionResultResponse> predictionResultResponses = predictionResults.stream().map(PredictionResultMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(predictionResultResponses);
    }

    @Operation(summary = "Get prediction results by model and links",
            description = "Returns prediction results based on the provided model and list of links.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prediction results found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<List<PredictionResultResponse>> getByModelAndLinks(
            @Parameter(description = "Model of the prediction results", required = true) @PathVariable String model,
            @Parameter(description = "List of prediction result links", required = true) @RequestBody GetPredictionsRequest getPredictionsRequest) {
        List<PredictionResult> predictionResults = predictionResultService.findByLinksAndModel(getPredictionsRequest.getLinks(), model);
        List<PredictionResultResponse> predictionResultResponses = predictionResults.stream().map(PredictionResultMapper::toResponse).toList();
        return ResponseEntity.status(HttpStatus.OK).body(predictionResultResponses);
    }

    @Operation(summary = "Delete prediction results by model",
            description = "Deletes all prediction results associated with the specified model.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prediction results deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Model not found", content = @Content)
    })
    @DeleteMapping("/model/{model}")
    public ResponseEntity<Void> deleteByModel(
            @Parameter(description = "Model of the prediction results to be deleted", required = true) @PathVariable String model) {
        predictionResultService.deleteByModel(model);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Add prediction",
            description = "Adds a new prediction result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Predictions added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PredictionResultResponse> create(@RequestBody @Valid PredictionResultRequest predictionResultRequest) {
        PredictionResult createdPredictionResult = predictionResultService.create(PredictionResultMapper.toModel(predictionResultRequest));
        PredictionResultResponse predictionResultResponse = PredictionResultMapper.toResponse(createdPredictionResult);
        return ResponseEntity.status(HttpStatus.OK).body(predictionResultResponse);
    }

    @Operation(summary = "Delete all prediction results",
            description = "Deletes all prediction results from the database.")
    @ApiResponse(responseCode = "200", description = "All prediction results deleted successfully")
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        predictionResultService.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
