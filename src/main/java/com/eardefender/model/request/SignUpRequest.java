package com.eardefender.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "Request Model for logging in and signing up")
@SuperBuilder
@Data
public class SignUpRequest extends CredentialsRequest {

    @Schema(description = "User's full name",
            example = "John Doe")
    @NotBlank(message = "Name must not be blank")
    private String name;
}
