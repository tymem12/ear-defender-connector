package com.eardefender.model.request;

import com.eardefender.model.InputParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Request Model for starting scraping through Scraper API")
@Data
public class BeginScrapingRequest {
    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String analysisId;
    @Schema(description = "Input parameters used for the analysis")
    private InputParams inputParams;
}
