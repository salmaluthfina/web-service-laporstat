/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.repository;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.Notification;
import com.salmaluthfina.pengaduan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByTimestampDesc(Long userId); // Mendapatkan semua notifikasi untuk pengguna tertentu
    List<Notification> findByRecipientOrderByTimestampDesc(String recipient); // Mendapatkan semua notifikasi berdasarkan penerima (USER, ADMIN_UTAMA, ADMIN_KATEGORI)
    List<Notification> findByRecipientAndTypeOrderByTimestampDesc(String recipient, String type);
    List<Notification> findByRecipient(String recipient);
    List<Notification> findByUserId(Long userId);
}
