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
}
