package com.eardefender.model;

import com.eardefender.validation.Timestamp;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PredictionResult implements Cloneable {
    @NotBlank(message = "Link must not be blank")
    private String link;
    @NotBlank(message = "Timestamp must not be blank")
    @Timestamp(message = "Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format")
    private String timestamp;
    @NotBlank(message = "Model must not be blank")
    private String model;
    @NotNull(message = "ModelPredictions must not be null")
    private List<SegmentPrediction> modelPredictions;

    @Override
    public PredictionResult clone() {
        try {
            PredictionResult clone = (PredictionResult) super.clone();
            clone.setModelPredictions(modelPredictions.stream().map(SegmentPrediction::clone).toList());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
