package com.eardefender.service;

import com.eardefender.model.User;
import com.eardefender.model.request.CredentialsRequest;

public interface AuthenticationService {

    User signup(CredentialsRequest input);

    User authenticate(CredentialsRequest request);
}
