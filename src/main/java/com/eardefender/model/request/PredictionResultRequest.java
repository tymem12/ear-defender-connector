package com.eardefender.model.request;

import com.eardefender.model.SegmentPrediction;
import com.eardefender.validation.Timestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for creating prediction of a file")
@Data
public class PredictionResultRequest {

    @Schema(description = "The URL link associated with the prediction result",
            example = "https://example.com/prediction")
    @NotBlank(message = "Link must not be blank")
    private String link;

    @Schema(description = "Timestamp of the prediction result in ISO 8601 format (YYYY-MM-DDThh:mm:ssTZD)",
            example = "2024-10-10T14:30:00Z")
    @NotBlank(message = "Timestamp must not be blank")
    @Timestamp(message = "Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format")
    private String timestamp;

    @Schema(description = "The name of the model used for predictions",
            example = "example-model")
    @NotBlank(message = "Model must not be blank")
    private String model;

    @Schema(description = "List of predictions generated by the model")
    @NotNull(message = "ModelPredictions must not be null")
    private List<SegmentPrediction> modelPredictions;
}
