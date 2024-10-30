package com.eardefender.security;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

    // getters and setters here...
}
