package com.eardefender.controller;

import com.eardefender.model.request.ScraperReportRequest;
import com.eardefender.service.ScraperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/scraper")
@RestController
@RequiredArgsConstructor
public class ScraperController {
    private final ScraperService scraperService;

    @Operation(summary = "Report scraping results for further processing",
            description = "Report scraping results for further processing by Model API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result reported successfully"),
            @ApiResponse(responseCode = "404", description = "Analysis not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
    })
    @PostMapping("/report")
    public ResponseEntity<Void> reportScrapingResults(@RequestBody @Valid ScraperReportRequest scraperReportRequest) {
        scraperService.reportScrapingResults(scraperReportRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
