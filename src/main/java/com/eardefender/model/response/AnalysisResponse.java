package com.eardefender.model.response;

import com.eardefender.model.InputParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Schema(description = "Base response model for an analysis")
@SuperBuilder
@Data
public class AnalysisResponse {

    @Schema(description = "Unique identifier for the analysis",
            example = "95a7ddce-4077-49f8-92a3-440842e04afe")
    private String id;

    @Schema(description = "Current status of the analysis",
            example = "DOWNLOADING")
    private String status;

    @Schema(description = "Timestamp of when the analysis was started",
            example = "2024-10-10T14:30:00Z")
    private String timestamp;

    @Schema(description = "Duration of the analysis in seconds",
            example = "1500")
    private Long duration;

    @Schema(description = "Input parameters used for the analysis")
    private InputParams inputParams;

    @Schema(description = "Number of files processed during the analysis",
            example = "10")
    private Integer fileCount;

    @Schema(description = "Number of deepfake files detected during the analysis",
            example = "6")
    private Integer deepfakeFileCount;
}
