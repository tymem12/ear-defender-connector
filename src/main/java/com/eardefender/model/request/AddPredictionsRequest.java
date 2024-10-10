package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Request model for adding prediction results")
public class AddPredictionsRequest {

    @NotEmpty(message = "Prediction results must not be empty")
    @Schema(description = "List of prediction results to be added")
    private List<PredictionResult> predictionResults;
}
