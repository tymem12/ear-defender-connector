package com.eardefender.model.request;

import com.eardefender.validation.Timestamp;
import io.swagger.v3.oas.annotations.media.Schema;

public class FinishAnalysisRequest {

    @Schema(description = "Timestamp indicating when the analysis finished, must follow ISO 8601 format (YYYY-MM-DDThh:mm:ssTZD)",
            example = "2024-10-10T14:30:00Z")
    @Timestamp(message = "Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format")
    private String finishTimestamp;
}
