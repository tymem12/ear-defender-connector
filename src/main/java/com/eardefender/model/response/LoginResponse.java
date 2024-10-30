package com.eardefender.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Response model returned after successful login attempt")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @Schema(description = "Generated jwt token",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXpvNTA1IiwiaWF0IjoxNzMwMzA4MTE1LCJleHAiOjE3MzAzOTQ1MTV9.pFS5PTzDNTZPLIGVQy5_8oEvujV-_prii5Zhuy8c_jo")
    private String token;
    @Schema(description = "Expiration time of the token in millis",
            example = "86400000")
    private long expiresIn;
}
