/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.ChangePasswordRequest;
import com.salmaluthfina.pengaduan.payload.UpdateProfileRequest;
import com.salmaluthfina.pengaduan.payload.UserProfileResponse;

public interface UserService {
    UserProfileResponse getProfile(String username);
    void updateProfile(String username, UpdateProfileRequest updateProfileRequest);
    void changePassword(String username, ChangePasswordRequest changePasswordRequest);
    void deleteAccount(String username); 
}