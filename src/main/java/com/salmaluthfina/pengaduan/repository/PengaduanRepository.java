/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.repository;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.enumeration.StatusPengaduan;
import com.salmaluthfina.pengaduan.enumeration.PrioritasPengaduan;
import com.salmaluthfina.pengaduan.enumeration.KategoriPengaduan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PengaduanRepository extends JpaRepository<Pengaduan, Long> {
    List<Pengaduan> findByUserId(Long userId); // Mendapatkan semua pengaduan berdasarkan ID pengguna
    List<Pengaduan> findByStatus(StatusPengaduan status); // Mendapatkan pengaduan berdasarkan status
    List<Pengaduan> findByPriority(PrioritasPengaduan priority); // Mendapatkan pengaduan berdasarkan prioritas
    List<Pengaduan> findByCategory(KategoriPengaduan category); // Mendapatkan pengaduan berdasarkan kategori

    @Query("SELECT p FROM Pengaduan p WHERE p.status = :status AND p.category = :category ORDER BY p.priority DESC")
    List<Pengaduan> findPengaduanByStatusAndKategoriOrderByPrioritasDesc(
            @Param("status") StatusPengaduan status,
            @Param("category") KategoriPengaduan category);
}
