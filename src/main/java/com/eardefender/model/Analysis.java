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
    private Integer duration;
    private InputParams inputParams;
    private Integer fileCount;
    private List<PredictionResult> predictions;

    @Data
    public static class InputParams {
        private String startingPoint;
        private Integer depth;
        private Integer maxFiles;
        private String model;
    }

    @Data
    public static class PredictionResult {
        private String link;
        private String timestamp;
        private String model;
        private List<SegmentPrediction> modelPredictions;

        @Data
        public static class SegmentPrediction {
            private Integer segmentNumber;
            private Integer label;
        }
    }
}
