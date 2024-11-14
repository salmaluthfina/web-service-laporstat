/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.exception.DuplicateUsernameException;
import com.salmaluthfina.pengaduan.exception.InvalidCredentialsException;
import com.salmaluthfina.pengaduan.payload.AuthRequest;
import com.salmaluthfina.pengaduan.payload.RegisterRequest;
import com.salmaluthfina.pengaduan.payload.AuthResponse;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.auth.JwtTokenProvider;
import com.salmaluthfina.pengaduan.exception.UserNotFoundException;
import com.salmaluthfina.pengaduan.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        
        String token = jwtTokenProvider.generateToken(user.getUsername());
        
        String role = user.getRole().toString();
        
        return new AuthResponse(token, user.getUsername(), role);
    }

    @Override
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new DuplicateUsernameException("Username is already taken.");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        userRepository.save(user);
    }
}