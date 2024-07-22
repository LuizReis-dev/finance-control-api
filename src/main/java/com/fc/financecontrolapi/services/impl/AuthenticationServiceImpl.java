package com.fc.financecontrolapi.services.impl;

import com.fc.financecontrolapi.dtos.auth.SignUpRequest;
import com.fc.financecontrolapi.dtos.auth.TokenResponse;
import com.fc.financecontrolapi.exceptions.user.UserAlreadyExistsException;
import com.fc.financecontrolapi.model.Role;
import com.fc.financecontrolapi.model.User;
import com.fc.financecontrolapi.repositories.UserRepository;
import com.fc.financecontrolapi.security.JwtService;
import com.fc.financecontrolapi.services.interfaces.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse signUp(SignUpRequest request) throws UserAlreadyExistsException {

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new UserAlreadyExistsException("User already exists!");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setRole(new Role(1L, "ROLE_USER"));
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return new TokenResponse(user.getId(), jwtToken);
    }
}
