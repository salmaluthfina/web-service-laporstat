/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.controller;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.PengaduanRequest;
import com.salmaluthfina.pengaduan.payload.PengaduanResponse;
import com.salmaluthfina.pengaduan.service.PengaduanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pengaduan")
@Tag(name = "Pengaduan", description = "API untuk mengelola pengaduan pelapor")
public class PengaduanController {

    private final PengaduanService pengaduanService;

    public PengaduanController(PengaduanService pengaduanService) {
        this.pengaduanService = pengaduanService;
    }

    @PostMapping
    public ResponseEntity<String> createPengaduan(@RequestBody PengaduanRequest pengaduanRequest, Authentication authentication) {
        String username = authentication.getName();
        pengaduanService.createPengaduan(username, pengaduanRequest);
        return ResponseEntity.ok("Pengaduan created successfully.");
    }

    @GetMapping
    public ResponseEntity<List<PengaduanResponse>> getPengaduanList(Authentication authentication) {
        String username = authentication.getName();
        List<PengaduanResponse> pengaduans = pengaduanService.getPengaduansForUser(username);
        return ResponseEntity.ok(pengaduans);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status, Authentication authentication) {
        String username = authentication.getName();
        pengaduanService.updateStatus(id, status, username);
        return ResponseEntity.ok("Pengaduan status updated successfully.");
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<String> updatePriority(@PathVariable Long id, @RequestParam String priority, Authentication authentication) {
        String username = authentication.getName();
        pengaduanService.updatePriority(id, priority, username);
        return ResponseEntity.ok("Pengaduan priority updated successfully.");
    }
    
    @PutMapping("/{id}/category")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestParam String category, Authentication authentication) {
        String username = authentication.getName();
        pengaduanService.updateCategory(id, category, username);
        return ResponseEntity.ok("Pengaduan category updated successfully.");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<String> markPengaduanAsCompleted(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        pengaduanService.markPengaduanAsCompleted(id, username);
        return ResponseEntity.ok("Pengaduan marked as completed.");
    }
}