/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.AuthRequest;
import com.salmaluthfina.pengaduan.payload.RegisterRequest;
import com.salmaluthfina.pengaduan.payload.AuthResponse;

public interface AuthService {
    AuthResponse authenticateUser(AuthRequest authRequest);
    void registerUser(RegisterRequest registerRequest);
}