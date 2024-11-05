package com.eardefender.controller;

import com.eardefender.config.ModelConfig;
import com.eardefender.config.ScraperConfig;
import com.eardefender.model.response.ModelConfigResponse;
import com.eardefender.model.response.ScraperConfigResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/config")
@RestController
@RequiredArgsConstructor
public class ConfigController {

    private final ModelConfig modelConfig;
    private final ScraperConfig scraperConfig;

    @Operation(summary = "Get config for model API",
            description = "Get validation config for model API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Config retrieved successfully")
    })
    @GetMapping("/model")
    public ResponseEntity<ModelConfigResponse> getModelConfig() {
        ModelConfigResponse response = ModelConfigResponse.builder()
                .models(modelConfig.getModels())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get config for scraper API",
            description = "Get validation config for scraper API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Config retrieved successfully")
    })
    @GetMapping("/scraper")
    public ResponseEntity<ScraperConfigResponse> getScraperConfig() {
        ScraperConfigResponse response = ScraperConfigResponse.builder()
                .maxDepth(scraperConfig.getMaxDepth())
                .maxFiles(scraperConfig.getMaxFiles())
                .maxPages(scraperConfig.getMaxPages())
                .maxTimePerFile(scraperConfig.getMaxTimePerFile())
                .maxTotalTime(scraperConfig.getMaxTotalTime())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
