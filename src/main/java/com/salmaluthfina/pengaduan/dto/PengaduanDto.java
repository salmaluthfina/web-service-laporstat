/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.dto;

/**
 *
 * @author salmaluthfinaa
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PengaduanDto {

    private Long id; // ID Pengaduan
    
    private String title; // Judul Pengaduan
    
    private String description; // Deskripsi Pengaduan
    
    private String kodeTiket;
    
    private LocalDateTime createdAt; // Tanggal Pengaduan Diajukan
    
    private String status; // Status Pengaduan
    
    private String priority; // Prioritas Pengaduan
    
    private String category; // Kategori Pengaduan
    
}
