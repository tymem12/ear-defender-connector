package com.eardefender.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Schema(description = "Path of associated file",
            example = "file.mp3")
    @NotBlank(message = "File path must not be null")
    private String filePath;

    @Schema(description = "Link of associated file",
            example = "https://link.pl")
    @NotBlank(message = "Link must not be null")
    private String link;
}
