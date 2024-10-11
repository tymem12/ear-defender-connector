package com.eardefender.model.request;

import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.validation.Status;
import com.eardefender.validation.Timestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "Request model for updating or creating an analysis")
@Data
public class AnalysisRequest {

    @Schema(description = "Current status of the analysis. Must be one of the following: DOWNLOADING, PROCESSING, FINISHED",
            example = "FINISHED")
    @Status(message = "Status must be one of the following: DOWNLOADING, PROCESSING, FINISHED")
    private String status;

    @Schema(description = "Timestamp indicating when the analysis finished, must follow ISO 8601 format (YYYY-MM-DDThh:mm:ssTZD)",
            example = "2024-10-10T14:30:00Z")
    @Timestamp(message = "Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format")
    private String finishTimestamp;

    @Schema(description = "Input parameters used for the analysis")
    private InputParams inputParams;

    @Schema(description = "List of prediction results associated with the analysis")
    private List<PredictionResult> predictionResults;
}
