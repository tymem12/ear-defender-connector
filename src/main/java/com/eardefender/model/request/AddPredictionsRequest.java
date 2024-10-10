package com.eardefender.model.request;

import com.eardefender.model.PredictionResult;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddPredictionsRequest {
    @NotEmpty(message = "Prediction results must not be empty")
    private List<PredictionResult> predictionResults;
}
