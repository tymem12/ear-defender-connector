package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "Request model for updating or creating an analysis")
@Data
public class AnalysisRequest {

    @Schema(description = "List of prediction results associated with the analysis")
    @NotNull(message = "Prediction result list must not be null")
    private List<PredictionResult> predictionResults;
}
