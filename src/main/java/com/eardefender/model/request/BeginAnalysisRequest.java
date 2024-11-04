package com.eardefender.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Schema(description = "Request model for beginning an analysis")
@Data
public class BeginAnalysisRequest {

    @Schema(description = "The starting URL for the scrapping process",
            example = "https://example.com/prediction")
    @NotBlank(message = "Starting point must not be blank")
    private String startingPoint;

    @Schema(description = "The depth for the scrapping process",
            example = "3")
    @NotNull(message = "Depth must not be null")
    @Positive(message = "Depth must be greater than 0")
    private Integer depth;

    @Schema(description = "The maximum number of files to download",
            example = "100")
    @NotNull(message = "Max files must not be null")
    @Positive(message = "Max files must be greater than 0")
    private Integer maxFiles;

    @Schema(description = "The model to be used for predictions",
            example = "example-model")
    @NotBlank(message = "Model must not be blank")
    private String model;

    @Schema(description = "The maximum number of pages to visit",
            example = "30")
    @NotNull(message = "Max pages must not be null")
    private Integer maxPages;

    @Schema(description = "Time in seconds after which downloading singular file will be aborted",
            example = "300")
    @NotNull(message = "Max time per file must not be null")
    private Integer maxTimePerFile;

    @Schema(description = "Time in seconds after which scraper won't visit any new sites",
            example = "1200")
    @NotNull(message = "Max total time must not be null")
    private Integer maxTotalTime;
}
