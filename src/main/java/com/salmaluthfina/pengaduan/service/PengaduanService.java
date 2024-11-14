/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.PengaduanRequest;
import com.salmaluthfina.pengaduan.payload.PengaduanResponse;

import java.util.List;

public interface PengaduanService {
    void createPengaduan(String username, PengaduanRequest pengaduanRequest);
    List<PengaduanResponse> getPengaduansForUser(String username);
    void updateStatus(Long pengaduanId, String status, String username);
    void updatePriority(Long pengaduanId, String priority, String username);
    void updateCategory(Long id, String category, String username);
    public void markPengaduanAsCompleted(Long pengaduanId, String username);
    public boolean canSubmitFeedback(Long pengaduanId);
}

