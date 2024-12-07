package com.eardefender.model.request;

import com.eardefender.validation.group.SignUpRequestGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "Request Model for logging in and signing up")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest extends CredentialsRequest {

    @Schema(description = "User's full name",
            example = "John Doe")
    @NotBlank(message = "Name must not be blank", groups = {SignUpRequestGroup.class})
    private String name;
}
