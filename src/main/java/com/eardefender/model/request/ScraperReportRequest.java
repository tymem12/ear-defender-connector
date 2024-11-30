package com.eardefender.model.request;

import com.eardefender.model.File;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for reporting results for the Scraper API")
@Data
public class ScraperReportRequest {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    @NotBlank(message = "Analysis id must not be blank")
    private String analysisId;

    @Schema(description = "List of newly downloaded files")
    @NotNull(message = "File list must not be null")
    private List<File> files;
}
