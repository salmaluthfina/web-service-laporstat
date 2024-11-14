/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.entity;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.enumeration.KategoriPengaduan;
import com.salmaluthfina.pengaduan.enumeration.PrioritasPengaduan;
import com.salmaluthfina.pengaduan.enumeration.StatusPengaduan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
public class Pengaduan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must be at most 50 characters")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @Column(unique = true, updatable = false)
    private String kodeTiket;
    
    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)  // Gunakan EnumType.STRING agar nilai enum disimpan sebagai String di database
    private StatusPengaduan status = StatusPengaduan.BERHASIL_DIBUAT; // Initial Status
    
    @Enumerated(EnumType.STRING)  // Gunakan EnumType.STRING untuk menyimpan nilai enum sebagai String
    private PrioritasPengaduan priority = PrioritasPengaduan.SEDANG; // Default priority

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)  // Enum untuk kategori pengaduan
    private KategoriPengaduan category;

    // Relationship with ChatMessage
    @OneToMany(mappedBy = "pengaduan")
    private List<ChatMessage> chatMessages;
    
    @OneToMany(mappedBy = "pengaduan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks;

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

}
