package com.eardefender.controller;

import com.eardefender.model.User;
import com.eardefender.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User Details",
            description = "Retrieves user details of the token owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data retrieved successfully"),
    })
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        User currentUser = userService.getLoggedInUser();

        return ResponseEntity.ok(currentUser);
    }
}