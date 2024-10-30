package com.eardefender.controller;

import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;
import com.eardefender.model.response.LoginResponse;
import com.eardefender.service.AuthenticationService;
import com.eardefender.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Sign up",
            description = "Creates an user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully."),
            @ApiResponse(responseCode = "409", description = "Account with given email already exist.", content = @Content),
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody CredentialsRequest credentialsRequest) {
        User registeredUser = authenticationService.signup(credentialsRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Sign in",
            description = "Generates token based on given credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody CredentialsRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}