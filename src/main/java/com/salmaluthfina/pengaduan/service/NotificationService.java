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
import com.salmaluthfina.pengaduan.entity.Notification;
import com.salmaluthfina.pengaduan.payload.NotificationResponse;
import java.util.List;

public interface NotificationService {
    void createNotification(String message, String recipient, User user, String type);
    void processTicketUpdate(String action, String role, User user, Long ticketId);
    void processChatNotification(String message, String recipient, User admin);
    List<NotificationResponse> getUserNotifications(Long userId); // Mendapatkan notifikasi untuk pengguna tertentu
    List<NotificationResponse> getNotificationsByRecipient(String recipient); // Mendapatkan notifikasi berdasarkan penerima
    void markNotificationAsRead(Long notificationId); // Menandai notifikasi tertentu sebagai terbaca
    void markNotificationAsRead(Long notificationId, String username); // Menandai notifikasi tertentu sebagai terbaca untuk pengguna tertentu
    void markAllAsRead(String username); // Menandai semua notifikasi sebagai terbaca untuk pengguna tertentu
}

