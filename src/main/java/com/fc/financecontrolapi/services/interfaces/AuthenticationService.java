package com.fc.financecontrolapi.services.interfaces;

import com.fc.financecontrolapi.dtos.auth.SignUpRequest;
import com.fc.financecontrolapi.dtos.auth.TokenResponse;
import com.fc.financecontrolapi.exceptions.user.UserAlreadyExistsException;

public interface AuthenticationService {
    TokenResponse signUp(SignUpRequest request) throws UserAlreadyExistsException;
}
