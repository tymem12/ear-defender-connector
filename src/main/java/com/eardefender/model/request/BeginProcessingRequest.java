package com.eardefender.model.request;

import com.eardefender.model.File;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for starting processing files through the Model API")
@Data
public class BeginProcessingRequest {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String analysisId;

    @Schema(description = "The model to be used for predictions",
            example = "example-model")
    private String model;

    @Schema(description = "List of newly downloaded files")
    @NotEmpty(message = "File list must not be empty")
    private List<File> files;
}
