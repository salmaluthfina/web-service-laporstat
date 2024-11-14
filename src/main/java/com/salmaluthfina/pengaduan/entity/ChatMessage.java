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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pengaduan_id")
    private Pengaduan pengaduan;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @NotBlank(message = "Message is required")
    @Size(max = 150, message = "Each message must be at most 150 characters")
    private String message;
    
    @CreationTimestamp
    private LocalDateTime timestamp = LocalDateTime.now();
}
