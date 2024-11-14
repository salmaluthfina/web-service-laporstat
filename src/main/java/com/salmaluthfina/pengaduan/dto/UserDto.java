/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.dto;

/**
 *
 * @author salmaluthfinaa
 */

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    
    private Long id; // ID pengguna
    private String username; // Username pengguna
    private String name; // Nama pengguna
    private String email; // Email pengguna
    private String phoneNumber; // Nomor telepon pengguna
    private String institution; // Asal instansi pengguna
    private String password; // Password pengguna
    private String role; // Role pengguna
    
    // Override method untuk mengembalikan authorities/roles untuk UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Anda bisa mengembalikan authorities atau role dalam bentuk Collection
        return null;
    }
    
    // Username biasanya adalah email dalam kasus ini
    @Override
    public String getUsername() {
        return this.email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true; // Asumsi akun tidak kedaluwarsa
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true; // Asumsi akun tidak terkunci
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Asumsi kredensial tidak kedaluwarsa
    }
    
    @Override
    public boolean isEnabled() {
        return true; // Asumsi akun aktif
    }
}