/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.enumeration.KategoriPengaduan;
import com.salmaluthfina.pengaduan.enumeration.PrioritasPengaduan;
import com.salmaluthfina.pengaduan.enumeration.Role;
import com.salmaluthfina.pengaduan.enumeration.StatusPengaduan;
import com.salmaluthfina.pengaduan.exception.ResourceNotFoundException;
import com.salmaluthfina.pengaduan.payload.PengaduanRequest;
import com.salmaluthfina.pengaduan.payload.PengaduanResponse;
import com.salmaluthfina.pengaduan.repository.PengaduanRepository;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.service.PengaduanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PengaduanServiceImpl implements PengaduanService {

    @Autowired
    private PengaduanRepository pengaduanRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public void createPengaduan(String username, PengaduanRequest pengaduanRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Pengaduan pengaduan = new Pengaduan();
        pengaduan.setUser(user);
        pengaduan.setTitle(pengaduanRequest.getTitle());
        pengaduan.setDescription(pengaduanRequest.getDescription());
        pengaduan.setKodeTiket(generateKodeTiket());
        pengaduan.setCreatedAt(LocalDateTime.now());

        if (pengaduanRequest.getPriority() != null) {
            pengaduan.setPriority(PrioritasPengaduan.valueOf(pengaduanRequest.getPriority().toUpperCase()));
        } else {
            pengaduan.setPriority(PrioritasPengaduan.SEDANG);
        }

        pengaduan.setStatus(StatusPengaduan.BERHASIL_DIBUAT); // Default status

        pengaduanRepository.save(pengaduan);
    }

    @Override
    public List<PengaduanResponse> getPengaduansForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Pengaduan> pengaduans;
        if (user.getRole().equals(Role.ADMIN_UTAMA) || user.getRole().equals(Role.ADMIN_KATEGORI)) {
            pengaduans = pengaduanRepository.findAll();
        } else {
            pengaduans = pengaduanRepository.findByUserId(user.getId());
        }

        return pengaduans.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long id, String status, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.ADMIN_UTAMA) && !user.getRole().equals(Role.ADMIN_KATEGORI)) {
            throw new RuntimeException("Unauthorized access: Only admin can update status.");
        }

        Pengaduan pengaduan = pengaduanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengaduan not found"));

        pengaduan.setStatus(StatusPengaduan.valueOf(status.toUpperCase()));
        pengaduanRepository.save(pengaduan);
        
        if (user.getRole().equals(Role.ADMIN_KATEGORI)) {
            notificationService.processTicketUpdate("updated status to " + status, "PELAPOR", user, id);
        }
        notificationService.processTicketUpdate("updated status to " + status, "ADMIN_UTAMA", user, id);
    }

    @Override
    public void updatePriority(Long id, String priority, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.ADMIN_UTAMA) && !user.getRole().equals(Role.ADMIN_KATEGORI)) {
            throw new RuntimeException("Unauthorized access: Only admin can update priority.");
        }

        Pengaduan pengaduan = pengaduanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengaduan not found"));

        pengaduan.setPriority(PrioritasPengaduan.valueOf(priority.toUpperCase()));

        pengaduanRepository.save(pengaduan);
        
        notificationService.processTicketUpdate("updated priority to " + priority, "ADMIN_UTAMA", user, id);
    }
    
    @Override
    public void updateCategory(Long id, String category, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.ADMIN_UTAMA)) {
            throw new RuntimeException("Unauthorized access: Only admin utama can update category.");
        }

        Pengaduan pengaduan = pengaduanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengaduan not found"));

        pengaduan.setCategory(KategoriPengaduan.valueOf(category.toUpperCase()));
        pengaduanRepository.save(pengaduan);
        
        notificationService.processTicketUpdate("updated category to " + category, "ADMIN_KATEGORI", user, id);
    }

    private PengaduanResponse mapToResponse(Pengaduan pengaduan) {
        return PengaduanResponse.builder()
                .id(pengaduan.getId())
                .title(pengaduan.getTitle())
                .description(pengaduan.getDescription())
                .status(pengaduan.getStatus().name())
                .priority(pengaduan.getPriority().name())
                .category(pengaduan.getCategory() != null ? pengaduan.getCategory().name() : null)
                .createdAt(pengaduan.getCreatedAt())
                .build();
    }

    private String generateKodeTiket() {
        return "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public void markPengaduanAsCompleted(Long pengaduanId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getRole().equals(Role.ADMIN_UTAMA) && !user.getRole().equals(Role.ADMIN_KATEGORI)) {
            throw new RuntimeException("Unauthorized access: Only admin can mark a complaint as completed.");
        }

        Pengaduan pengaduan = pengaduanRepository.findById(pengaduanId)
                .orElseThrow(() -> new ResourceNotFoundException("Pengaduan not found"));

        pengaduan.setStatus(StatusPengaduan.SELESAI);
        pengaduanRepository.save(pengaduan);
    }

    public boolean canSubmitFeedback(Long pengaduanId) {
        Pengaduan pengaduan = pengaduanRepository.findById(pengaduanId)
                .orElseThrow(() -> new ResourceNotFoundException("Pengaduan not found"));
        return pengaduan.getStatus() == StatusPengaduan.SELESAI;
    }
    
    
}

