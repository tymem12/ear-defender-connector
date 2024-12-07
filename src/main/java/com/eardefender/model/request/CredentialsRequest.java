package com.eardefender.model.request;

import com.eardefender.validation.Password;
import com.eardefender.validation.group.SignInRequestGroup;
import com.eardefender.validation.group.SignUpRequestGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(description = "Request Model for logging in and signing up")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsRequest {

    @Schema(description = "User's email",
            example = "dezo505@gmail.com")
    @NotBlank(message = "Email must not be blank", groups = {SignUpRequestGroup.class, SignInRequestGroup.class})
    private String email;

    @Schema(description = "User's password",
            example = "MySecret1234!")
    @NotBlank(message = "Password must not be blank", groups = {SignUpRequestGroup.class, SignInRequestGroup.class})
    @Password(groups = SignUpRequestGroup.class)
    private String password;
}
