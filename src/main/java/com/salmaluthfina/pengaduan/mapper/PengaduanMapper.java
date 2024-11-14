/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.mapper;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.dto.PengaduanDto;
import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.enumeration.KategoriPengaduan;
import com.salmaluthfina.pengaduan.enumeration.PrioritasPengaduan;
import com.salmaluthfina.pengaduan.enumeration.StatusPengaduan;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PengaduanMapper {

    public PengaduanDto toDto(Pengaduan pengaduan) {
        return PengaduanDto.builder()
                .id(pengaduan.getId())
                .title(pengaduan.getTitle())
                .description(pengaduan.getDescription())
                .kodeTiket(pengaduan.getKodeTiket())
                .createdAt(pengaduan.getCreatedAt())
                .status(pengaduan.getStatus() != null ? pengaduan.getStatus().name() : null)
                .priority(pengaduan.getPriority() != null ? pengaduan.getPriority().name() : null)
                .category(pengaduan.getCategory() != null ? pengaduan.getCategory().name() : null)
                .build();
    }

    public Pengaduan toEntity(PengaduanDto pengaduanDto, User user) {
        Pengaduan pengaduan = new Pengaduan();
        pengaduan.setId(pengaduanDto.getId());
        pengaduan.setTitle(pengaduanDto.getTitle());
        pengaduan.setDescription(pengaduanDto.getDescription());
        pengaduan.setKodeTiket(pengaduanDto.getKodeTiket());
        pengaduan.setCreatedAt(pengaduanDto.getCreatedAt() != null ? pengaduanDto.getCreatedAt() : LocalDateTime.now());
        
        if (pengaduanDto.getStatus() != null) {
            pengaduan.setStatus(StatusPengaduan.valueOf(pengaduanDto.getStatus()));
        }
        if (pengaduanDto.getPriority() != null) {
            pengaduan.setPriority(PrioritasPengaduan.valueOf(pengaduanDto.getPriority()));
        }
        if (pengaduanDto.getCategory() != null) {
            pengaduan.setCategory(KategoriPengaduan.valueOf(pengaduanDto.getCategory()));
        }

        pengaduan.setUser(user);
        
        return pengaduan;
    }
}
