package com.eardefender.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Schema(description = "Response model for scraper API configuration")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScraperConfigResponse {

    @Schema(description = "Max allowed depth allowed for scraping",
            example = "3")
    private int maxDepth;

    @Schema(description = "Max allowed files downloaded during scraping",
            example = "100")
    private int maxFiles;

    @Schema(description = "Max allowed pages visited during scraping",
            example = "50")
    private int maxPages;

    @Schema(description = "Max allowed time for downloading single file during scraping",
            example = "60")
    private int maxTimePerFile;

    @Schema(description = "Max allowed time for total scraping process",
            example = "1200")
    private int maxTotalTime;
}
