package com.eardefender.model.request;

import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import com.eardefender.validation.Status;
import com.eardefender.validation.Timestamp;
import lombok.Data;

import java.util.List;

@Data
public class AnalysisRequest {
    @Status(message = "Status must be one of the following: DOWNLOADING, PROCESSING, FINISHED")
    private String status;
    @Timestamp(message = "Timestamp must follow ISO 8601 (YYYY-MM-DDThh:mm:ssTZD) format")
    private String finishTimestamp;
    private InputParams inputParams;
    private List<PredictionResult> predictionResults;
}
