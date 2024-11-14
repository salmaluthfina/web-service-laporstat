/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.mapper;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.dto.NotificationDto;
import com.salmaluthfina.pengaduan.entity.Notification;
import com.salmaluthfina.pengaduan.entity.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationDto toDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .timestamp(notification.getTimestamp())
                .recipient(notification.getRecipient())
                .build();
    }

    public Notification toEntity(NotificationDto notificationDto, User user) {
        Notification notification = new Notification();
        notification.setId(notificationDto.getId());
        notification.setMessage(notificationDto.getMessage());
        notification.setTimestamp(notificationDto.getTimestamp() != null ? notificationDto.getTimestamp() : LocalDateTime.now());
        notification.setRecipient(notificationDto.getRecipient());
        notification.setUser(user);
        notification.setRead(false); // Default ke unread status saat notifikasi baru dibuat
        return notification;
    }
}

