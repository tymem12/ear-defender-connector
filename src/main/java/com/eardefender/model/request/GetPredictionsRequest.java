package com.eardefender.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Schema(description = "Request Model for retrieving predictions")
@Data
public class GetPredictionsRequest {

    @NotEmpty(message = "Links must not be empty")
    private List<String> links;
}
