package com.eardefender.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Response model for scraper API configuration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelConfigResponse {

    @Schema(description = "Allowed models for processing via model API",
            example = "[\"model1\", \"model2\", \"model3\"]")
    private List<String> models;
}
