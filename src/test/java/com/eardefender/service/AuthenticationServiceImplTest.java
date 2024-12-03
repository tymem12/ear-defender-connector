package com.eardefender.service;

import com.eardefender.exception.UserAlreadyExistException;
import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;
import com.eardefender.model.request.SignUpRequest;
import com.eardefender.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;
    private CredentialsRequest credentialsRequest;
    private SignUpRequest signUpRequest;

    @BeforeEach
    protected void setUp() throws Exception {
        setUpUser();
        setUpCredentialRequests();
        setUpSignUpRequests();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void signUp_UserAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> authenticationService.signup(signUpRequest));
    }

    @Test
    public void signUp_UserNotExists_CreatesUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.signup(signUpRequest);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any());

        assertEquals(user, result);
    }

    @Test
    public void authenticate_UserExists_AuthenticateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User result = authenticationService.authenticate(credentialsRequest);

        assertEquals(user, result);
    }

    @Test
    public void authenticate_UserNotExists_DoesNotAuthenticateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(credentialsRequest));
    }

    private void setUpUser() {
        user = User.builder()
                .id("uuid")
                .email("email@email.com")
                .password("passwordHash")
                .fullName("name")
                .build();
    }

    private void setUpCredentialRequests() {
        credentialsRequest = CredentialsRequest.builder()
                .email("new@email.com")
                .password("newPass")
                .build();
    }

    private void setUpSignUpRequests() {
        signUpRequest = SignUpRequest.builder()
                .email("new@email.com")
                .password("newPass")
                .name("name")
                .build();
    }
}
