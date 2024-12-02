package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "Request model for adding prediction results")
@Data
public class AddPredictionsRequest {

    @NotNull(message = "Prediction results must not be null")
    @Schema(description = "List of prediction results to be added")
    private List<PredictionResult> predictionResults;
}
