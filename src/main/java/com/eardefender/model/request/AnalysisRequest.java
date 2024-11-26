package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Schema(description = "Request model for updating or creating an analysis")
@Data
public class AnalysisRequest {

    @Schema(description = "List of prediction results associated with the analysis")
    @NotEmpty(message = "Prediction result list must not be empty")
    private List<PredictionResult> predictionResults;
}
