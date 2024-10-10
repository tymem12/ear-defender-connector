package com.eardefender.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "Analyses")
public class Analysis {

    @Id
    private String id;
    private String status;
    private String timestamp;
    private Long duration;
    private InputParams inputParams;
    private Integer fileCount;
    private List<PredictionResult> predictionResults;
}
