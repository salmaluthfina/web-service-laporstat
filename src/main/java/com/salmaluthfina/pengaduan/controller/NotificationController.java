/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.controller;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.payload.NotificationResponse;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "API untuk mengelola notifikasi pelapor dan admin")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    public NotificationController(NotificationService notificationService, UserRepository userRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
    }

    // Endpoint untuk mendapatkan semua notifikasi untuk pengguna
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getNotifications(Authentication authentication) {
        String username = authentication.getName();
        
        // Menemukan User berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<NotificationResponse> notifications;

        if (user.getRole().equals("ADMIN_UTAMA")) {
            notifications = notificationService.getNotificationsByRecipient("ADMIN_UTAMA");
        } else if (user.getRole().equals("ADMIN_KATEGORI")) {
            notifications = notificationService.getNotificationsByRecipient("ADMIN_KATEGORI");
        } else {
            notifications = notificationService.getUserNotifications(user.getId());
        }
        
        return ResponseEntity.ok(notifications);
    }

    // Endpoint untuk menandai notifikasi sebagai sudah dibaca
    @PutMapping("/{notificationId}/mark-as-read")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId, Authentication authentication) {
        String username = authentication.getName();
        
        // Menemukan User berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        notificationService.markNotificationAsRead(notificationId, username);
        return ResponseEntity.ok("Notification marked as read.");
    }

    // Endpoint untuk menandai semua notifikasi sebagai sudah dibaca
    @PutMapping("/mark-all-as-read")
    public ResponseEntity<String> markAllAsRead(Authentication authentication) {
        String username = authentication.getName();
        // Menemukan User berdasarkan username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        notificationService.markAllAsRead(username);
        return ResponseEntity.ok("All notifications marked as read.");
    }
}
