/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.entity;

/**
 *
 * @author salmaluthfinaa
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();

    private String recipient; // USER, ADMIN_UTAMA, ADMIN_KATEGORI

    private boolean isRead = false; // Default value false, indicating unread status
    
    private String type; 

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
