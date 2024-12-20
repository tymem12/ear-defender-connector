package com.eardefender.model;

import com.eardefender.model.request.BeginAnalysisRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputParams implements Cloneable {

    @Schema(description = "The starting URL for the scrapping process",
            example = "https://example.com/prediction")
    private String startingPoint;

    @Schema(description = "The depth for the scrapping process",
            example = "3")
    private Integer maxDepth;

    @Schema(description = "The maximum number of files to download",
            example = "100")
    private Integer maxFiles;

    @Schema(description = "The model to be used for predictions",
            example = "example-model")
    private String model;

    @Schema(description = "The maximum number of pages to visit",
            example = "30")
    private Integer maxPages;

    @Schema(description = "Time in seconds after which downloading singular file will be aborted",
            example = "300")
    private Integer maxTimePerFile;

    @Schema(description = "Time in seconds after which scraper won't visit any new sites",
            example = "1200")
    private Integer maxTotalTime;

    @Override
    public InputParams clone() {
        try {
            return (InputParams) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static InputParams createFromRequest(BeginAnalysisRequest request) {
        return InputParams.builder()
                .maxDepth(request.getDepth())
                .model(request.getModel())
                .maxFiles(request.getMaxFiles())
                .startingPoint(request.getStartingPoint())
                .maxPages(request.getMaxPages())
                .maxTimePerFile(request.getMaxTimePerFile())
                .maxTotalTime(request.getMaxTotalTime())
                .build();
    }
}
