package com.eardefender.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SegmentPrediction implements Cloneable {
    @NotNull(message = "Segment number must not be null")
    @Min(value = 0, message = "Segment number must not be negative")
    private Integer segmentNumber;
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
