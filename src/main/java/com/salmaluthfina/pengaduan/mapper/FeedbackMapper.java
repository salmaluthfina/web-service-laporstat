/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.mapper;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.dto.FeedbackDto;
import com.salmaluthfina.pengaduan.entity.Feedback;
import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    public FeedbackDto toDto(Feedback feedback) {
        return FeedbackDto.builder()
                .id(feedback.getId())
                .pengaduId(feedback.getUser().getId())
                .pengaduanId(feedback.getPengaduan().getId())
                .rating(calculateAverageRating(feedback))
                .komentar(feedback.getMessage())
                .build();
    }

    public Feedback toEntity(FeedbackDto feedbackDto, User user, Pengaduan pengaduan) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackDto.getId());
        feedback.setUser(user);
        feedback.setPengaduan(pengaduan);
        
        // Misalnya set rating sebagai nilai yang sama jika hanya 1 nilai rating pada DTO
        int rating = feedbackDto.getRating();
        feedback.setKeramahanRating(rating);
        feedback.setKualitasRating(rating);
        feedback.setKecepatanRating(rating);
        feedback.setKemudahanRating(rating);
        
        feedback.setMessage(feedbackDto.getKomentar());
        return feedback;
    }
    
    private int calculateAverageRating(Feedback feedback) {
        int total = feedback.getKeramahanRating() 
                  + feedback.getKualitasRating() 
                  + feedback.getKecepatanRating() 
                  + feedback.getKemudahanRating();
        return total / 4;
    }
}
