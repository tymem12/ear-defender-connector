package com.eardefender.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for starting processing files through the Model API")
@Data
public class StartProcessingRequest {
    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String analysisId;
    @Schema(description = "The model to be used for predictions",
            example = "example-model")
    private String model;
    @Schema(description = "List paths to newly downloaded files", example = "[\"file1.mp3\", \"file2.mp3\", \"file3.mp3\"]")
    private List<String> newFilePaths;
}
