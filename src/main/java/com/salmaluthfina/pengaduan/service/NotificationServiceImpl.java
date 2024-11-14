/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.Notification;
import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.repository.NotificationRepository;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.service.NotificationService;
import com.salmaluthfina.pengaduan.payload.NotificationResponse;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createNotification(String message, String recipient, User user, String type) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(recipient);
        notification.setUser(user);
        notification.setType(type);
        notificationRepository.save(notification);
    }
    
    public void processTicketUpdate(String action, String role, User user, Long ticketId) {
        String message = "Pengaduan " + action + " pada laporan ID: " + ticketId;
        String recipient = "";

        // Determine the recipient based on the action and role
        if (role.equals("PELAPOR")) {
            if (action.contains("status")) {
                recipient = "PELAPOR";
            } else if (action.contains("chat")) {
                recipient = "PELAPOR";
            }
        } else if (role.equals("ADMIN_UTAMA")) {
            if (action.contains("baru")) {
                recipient = "ADMIN_UTAMA";
            } else if (action.contains("kategori")) {
                recipient = "ADMIN_UTAMA";
            }
        } else if (role.equals("ADMIN_KATEGORI")) {
            if (action.contains("kategori")) {
                recipient = "ADMIN_KATEGORI";
            }
        }

        // Send notification
        createNotification(message, recipient, user, "TICKET_UPDATE");
    }
    
    // Method for chat notifications from ADMIN_KATEGORI
    public void processChatNotification(String message, String recipient, User admin) {
        createNotification(message, recipient, admin, "CHAT");
    }
    
    public List<NotificationResponse> getNotificationsByRecipient(String recipient) {
        List<Notification> notifications = notificationRepository.findByRecipient(recipient);
        return notifications.stream()
                .map(notification -> new NotificationResponse(notification.getMessage(), notification.getTimestamp(), notification.getType()))
                .collect(Collectors.toList());
    }

    public List<NotificationResponse> getUserNotifications(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notification -> new NotificationResponse(notification.getMessage(), notification.getTimestamp(), notification.getType()))
                .collect(Collectors.toList());
    }
    

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true); // Mengatur status sebagai sudah dibaca
        notificationRepository.save(notification);
    }

    @Override
    public void markNotificationAsRead(Long notificationId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Notification does not belong to the user");
        }

        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Notification> notifications = notificationRepository.findByUserIdOrderByTimestampDesc(user.getId());
        notifications.forEach(notification -> notification.setRead(true));
        
        notificationRepository.saveAll(notifications);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .isRead(notification.isRead()) // Memetakan status isRead
                .timestamp(notification.getTimestamp())
                .build();
    }
}

