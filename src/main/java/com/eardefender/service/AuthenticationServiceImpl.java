package com.eardefender.service;

import com.eardefender.exception.UserAlreadyExistException;
import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;
import com.eardefender.model.request.SignUpRequest;
import com.eardefender.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public User signup(SignUpRequest input) {
        userRepository.findByEmail(input.getEmail())
                .ifPresent(u -> {throw new UserAlreadyExistException(u.getUsername());});

        User user = User.builder()
                .fullName(input.getName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User authenticate(CredentialsRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(input.getEmail()));
    }
}
