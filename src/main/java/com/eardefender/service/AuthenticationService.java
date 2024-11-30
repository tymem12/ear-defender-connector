package com.eardefender.service;

import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;
import com.eardefender.model.request.SignUpRequest;

public interface AuthenticationService {

    User signup(SignUpRequest input);

    User authenticate(CredentialsRequest request);
}
