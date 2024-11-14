/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.controller;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.UpdateProfileRequest;
import com.salmaluthfina.pengaduan.payload.ChangePasswordRequest;
import com.salmaluthfina.pengaduan.payload.UserProfileResponse;
import com.salmaluthfina.pengaduan.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Notification", description = "API untuk mengelola profile")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        String username = authentication.getName();
        UserProfileResponse profile = userService.getProfile(username);
        return ResponseEntity.ok(profile);
    }
    
    
    @PutMapping("/profile/edit")
    public ResponseEntity<String> editProfile(@RequestBody UpdateProfileRequest updateProfileRequest, Authentication authentication) {
        String username = authentication.getName();
        userService.updateProfile(username, updateProfileRequest);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    
    @PutMapping("/profile/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        String username = authentication.getName();
        userService.changePassword(username, changePasswordRequest);
        return ResponseEntity.ok("Password changed successfully.");
    }
    
    
    @DeleteMapping("/profile/delete")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        String username = authentication.getName();
        userService.deleteAccount(username);
        return ResponseEntity.ok("Account deleted successfully.");
    }
}