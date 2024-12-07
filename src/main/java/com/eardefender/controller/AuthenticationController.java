package com.eardefender.controller;

import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;
import com.eardefender.model.request.SignUpRequest;
import com.eardefender.model.response.LoginResponse;
import com.eardefender.service.AuthenticationService;
import com.eardefender.service.JwtService;
import com.eardefender.validation.group.SignInRequestGroup;
import com.eardefender.validation.group.SignUpRequestGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Operation(summary = "Sign up",
            description = "Creates an user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully."),
            @ApiResponse(responseCode = "409", description = "Account with given email already exist.", content = @Content),
    })
    @PostMapping("/signup")
    public ResponseEntity<User> register(@Validated(SignUpRequestGroup.class) @RequestBody SignUpRequest signUpRequest) {
        User registeredUser = authenticationService.signup(signUpRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Sign in",
            description = "Generates token based on given credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Validated(SignInRequestGroup.class) @RequestBody CredentialsRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}