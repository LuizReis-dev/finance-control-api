package com.fc.financecontrolapi.services.impl;

import com.fc.financecontrolapi.dtos.auth.SignInRequest;
import com.fc.financecontrolapi.dtos.auth.SignUpRequest;
import com.fc.financecontrolapi.dtos.auth.TokenResponse;
import com.fc.financecontrolapi.exceptions.user.AuthenticationException;
import com.fc.financecontrolapi.exceptions.user.UserAlreadyExistsException;
import com.fc.financecontrolapi.model.Role;
import com.fc.financecontrolapi.model.User;
import com.fc.financecontrolapi.repositories.UserRepository;
import com.fc.financecontrolapi.security.JwtService;
import com.fc.financecontrolapi.services.interfaces.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

    @Override
    public TokenResponse signIn(SignInRequest request) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if(!authentication.isAuthenticated()) throw new AuthenticationException("Not authenticated");

        User user = (User) authentication.getPrincipal();
        var jwtToken = jwtService.generateToken(user);
        return new TokenResponse(user.getId(), jwtToken);
    }

    @Override
    public User getAuthenticatedUser() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userRepository.findByEmail(userName)
                .orElseThrow(()-> new AuthenticationException("User not found"));
    }
}
