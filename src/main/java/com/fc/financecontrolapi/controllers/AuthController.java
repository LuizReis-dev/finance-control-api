package com.fc.financecontrolapi.controllers;

import com.fc.financecontrolapi.dtos.auth.SignInRequest;
import com.fc.financecontrolapi.dtos.auth.SignUpRequest;
import com.fc.financecontrolapi.dtos.auth.TokenResponse;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.exceptions.user.UserAlreadyExistsException;
import com.fc.financecontrolapi.services.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signUp(@Valid @RequestBody SignUpRequest request) throws UserAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.signUp((request)));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody SignInRequest request) throws AuthenticationException {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signIn((request)));
    }
}
