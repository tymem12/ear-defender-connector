package com.eardefender.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Model representing the prediction for a specific file segment")
@Data
public class SegmentPrediction implements Cloneable {

    @Schema(description = "The segment number for which the prediction is made",
            example = "0")
    @NotNull(message = "Segment number must not be null")
    @Min(value = 0, message = "Segment number must not be negative")
    private Integer segmentNumber;

    @Schema(description = "The predicted label for the segment",
            example = "1")
    @NotNull(message = "Label must not be null")
    private Integer label;

    @Override
    public SegmentPrediction clone() {
        try {
            return (SegmentPrediction) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
