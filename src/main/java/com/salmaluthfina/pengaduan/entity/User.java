/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.entity;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
//import jakarta.management.relation.Role;
import lombok.Data;
import lombok.*;
//import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username must be at most 20 characters")
    @Pattern(regexp = "^(?=\\D*\\d{0,5}\\D*$)[a-zA-Z0-9]+$", 
             message = "Username can contain only letters and up to 5 numbers without special characters")
    private String username;
    
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", 
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$", 
//             message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;
    
    private String name;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number must contain only digits")
    private String phoneNumber;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.PELAPOR;
    
    // Relationship with Pengaduan
    @OneToMany(mappedBy = "user")
    private List<Pengaduan> pengaduanList;
    
//    @Column(name = "profile")
//    private String profile;
    
}
