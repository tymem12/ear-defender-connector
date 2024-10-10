package com.eardefender.model.response;

import com.eardefender.model.InputParams;
import com.eardefender.model.PredictionResult;
import lombok.Data;
import java.util.List;

@Data
public class AnalysisResponse {
    private String id;
    private String status;
    private String timestamp;
    private Long duration;
    private InputParams inputParams;
    private Integer fileCount;
    private List<PredictionResult> predictions;
}
