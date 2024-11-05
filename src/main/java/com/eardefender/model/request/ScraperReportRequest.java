package com.eardefender.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for reporting results for the Scraper API")
@Data
public class ScraperReportRequest {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String analysisId;

    @Schema(description = "List paths to newly downloaded files", example = "[\"file1.mp3\", \"file2.mp3\", \"file3.mp3\"]")
    private List<String> newFilePaths;
}
