package com.eardefender.model.request;

import com.eardefender.model.InputParams;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Schema(description = "Request Model for starting scraping through Scraper API")
@Builder
@Data
public class BeginScrapingRequest {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    @NotBlank(message = "Analysis id must not be blank")
    private String analysisId;

    @Schema(description = "Input parameters used for the analysis")
    private InputParams inputParams;
}
