package com.eardefender.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Schema(description = "Model representing user in the system")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User implements UserDetails {
    @Schema(description = "Unique identifier for the user",
            example = "672186ac88ae2644b67de303")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Schema(description = "Full name of the user",
            example = "John Doe")
    private String fullName;

    @Schema(description = "Unique email of the user",
            example = "Example@example.com")
    private String email;

    @Schema(description = "Hashed password of the user",
            example = "$2a$10$3h6b4bYhTC7wR/k98xNjnuowOIlRTlgyK.f/ca5RiLhin461Y9m4q")
    private String password;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}