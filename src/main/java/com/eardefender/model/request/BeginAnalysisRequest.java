package com.eardefender.model.request;

import com.eardefender.validation.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

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
    @Range(min = 1, max = 5, message = "Depth must be between 1 and 5")
    private Integer depth;

    @Schema(description = "The maximum number of files to download",
            example = "40")
    @NotNull(message = "Max files must not be null")
    @Range(min = 1, max = 50, message = "Max files must be between 1 and 50")
    private Integer maxFiles;

    @Schema(description = "The model to be used for predictions",
            example = "mesonet")
    @NotBlank(message = "Model must not be blank")
    @Model
    private String model;

    @Schema(description = "The maximum number of pages to visit",
            example = "30")
    @NotNull(message = "Max pages must not be null")
    @Range(min = 1, max = 1000, message = "Max pages must be between 1 and 1000")
    private Integer maxPages;

    @Schema(description = "Time in seconds after which downloading singular file will be aborted",
            example = "60")
    @NotNull(message = "Max time per file must not be null")
    @Range(min = 1, max = 120, message = "Max time per file must be between 1 and 120")
    private Integer maxTimePerFile;

    @Schema(description = "Time in seconds after which scraper won't visit any new sites",
            example = "1200")
    @NotNull(message = "Max total time must not be null")
    @Range(min = 1, max = 3600, message = "Max total time must be between 1 and 3600")
    private Integer maxTotalTime;
}
