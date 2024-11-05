package com.eardefender.model.response;

import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "Response model for an analysis")
@Builder
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

    @Schema(description = "List of prediction results generated during the analysis")
    private List<PredictionResult> predictionResults;
}
