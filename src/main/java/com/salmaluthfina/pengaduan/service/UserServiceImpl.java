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
import com.salmaluthfina.pengaduan.enumeration.Role;
import com.salmaluthfina.pengaduan.exception.UserNotFoundException;
import com.salmaluthfina.pengaduan.payload.ChangePasswordRequest;
import com.salmaluthfina.pengaduan.payload.UpdateProfileRequest;
import com.salmaluthfina.pengaduan.payload.UserProfileResponse;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserProfileResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return new UserProfileResponse(
            user.getUsername(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getName(),
            user.getRole().name()  // Konversi enum Role ke String
        );
    }

    @Override
    @Transactional
    public void updateProfile(String username, UpdateProfileRequest updateProfileRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        
        user.setEmail(updateProfileRequest.getEmail());
        user.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        user.setName(updateProfileRequest.getName());
        user.setUsername(updateProfileRequest.getUsername());
        
        // Validasi role dan hanya ubah jika role tidak null
        if (updateProfileRequest.getRole() != null) {
            try {
                Role role = Role.valueOf(updateProfileRequest.getRole().toUpperCase());  
                user.setRole(role);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role provided.");
            }
        }


        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));
        
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect.");
        }
        
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
    
    @Override
    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        userRepository.delete(user);
    }
}
