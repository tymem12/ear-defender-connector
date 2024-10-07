package com.eardefender.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AnalysisRequest {

    @NotBlank(message = "Starting point must not be blank")
    private String startingPoint;

    @NotNull(message = "Depth must not be null")
    @Positive(message = "Depth must be greater than 0")
    private Integer depth;

    @NotNull(message = "Max files must not be null")
    @Positive(message = "Max files must be greater than 0")
    private Integer maxFiles;

    @NotBlank(message = "Model must not be blank")
    private String model;
}
