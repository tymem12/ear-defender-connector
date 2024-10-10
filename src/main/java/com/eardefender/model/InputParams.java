package com.eardefender.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InputParams implements Cloneable {

    @Schema(description = "The starting URL for the scrapping process",
            example = "https://example.com/prediction")
    private String startingPoint;

    @Schema(description = "The depth for the scrapping process",
            example = "3")
    private Integer depth;

    @Schema(description = "The maximum number of files to download",
            example = "100")
    private Integer maxFiles;

    @Schema(description = "The model to be used for predictions",
            example = "example-model")
    private String model;

    @Override
    public InputParams clone() {
        try {
            return (InputParams) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
