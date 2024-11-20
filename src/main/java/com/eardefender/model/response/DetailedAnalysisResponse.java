package com.eardefender.model.response;

import com.eardefender.model.PredictionResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Schema(description = "Detailed response model for an analysis")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedAnalysisResponse extends AnalysisResponse {

    @Schema(description = "List of prediction results generated during the analysis")
    private List<PredictionResult> predictionResults;
}
