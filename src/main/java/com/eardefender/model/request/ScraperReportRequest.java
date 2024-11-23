package com.eardefender.model.request;

import com.eardefender.model.File;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for reporting results for the Scraper API")
@Data
public class ScraperReportRequest {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String analysisId;

    @Schema(description = "List of newly downloaded files")
    private List<File> files;
}
