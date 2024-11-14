/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.Feedback;
import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.enumeration.Role;
import com.salmaluthfina.pengaduan.enumeration.StatusPengaduan;
import com.salmaluthfina.pengaduan.exception.InvalidActionException;
import com.salmaluthfina.pengaduan.exception.ResourceNotFoundException;
import com.salmaluthfina.pengaduan.payload.FeedbackRequest;
import com.salmaluthfina.pengaduan.payload.FeedbackResponse;
import com.salmaluthfina.pengaduan.repository.FeedbackRepository;
import com.salmaluthfina.pengaduan.repository.PengaduanRepository;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PengaduanRepository pengaduanRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FeedbackResponse createFeedback(Long pengaduanId, FeedbackRequest feedbackRequest, String username) {
        Pengaduan pengaduan = pengaduanRepository.findById(pengaduanId)
                .orElseThrow(() -> new ResourceNotFoundException("Pengaduan not found"));

        if (pengaduan.getStatus() != StatusPengaduan.SELESAI) {
            throw new InvalidActionException("Feedback can only be submitted for completed tickets.");
        }
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        // Memastikan hanya pelapor yang dapat mengirimkan feedback
        if (!user.getRole().equals(Role.PELAPOR)) {
            throw new RuntimeException("Only the reporter can submit feedback.");
        }

        Feedback feedback = new Feedback();
        feedback.setPengaduan(pengaduan);
        feedback.setUser(user);
        feedback.setKeramahanRating(feedbackRequest.getKeramahanRating());
        feedback.setKualitasRating(feedbackRequest.getKualitasRating());
        feedback.setKecepatanRating(feedbackRequest.getKecepatanRating());
        feedback.setKemudahanRating(feedbackRequest.getKemudahanRating());
        feedback.setMessage(feedbackRequest.getMessage());
        feedback.setPelaporName(user.getUsername());
        feedback.setPelaporEmail(user.getEmail());
        feedback.setPelaporPhoneNumber(user.getPhoneNumber());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return mapToFeedbackResponse(savedFeedback);
    }

    @Override
    public FeedbackResponse getFeedbackByPengaduanId(Long pengaduanId) {
        Feedback feedback = feedbackRepository.findByPengaduanId(pengaduanId);
        
        // Jika feedback tidak ada, kembalikan null atau throw exception jika perlu
        if (feedback == null) {
            throw new ResourceNotFoundException("Feedback not found for this pengaduan");
        }
        
        return mapToFeedbackResponse(feedback);
    }

    private FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        FeedbackResponse response = new FeedbackResponse();
        response.setKeramahanRating(feedback.getKeramahanRating());
        response.setKualitasRating(feedback.getKualitasRating());
        response.setKecepatanRating(feedback.getKecepatanRating());
        response.setKemudahanRating(feedback.getKemudahanRating());
        response.setMessage(feedback.getMessage());
        response.setPelaporName(feedback.getPelaporName());
        response.setPelaporEmail(feedback.getPelaporEmail());
        response.setPelaporPhoneNumber(feedback.getPelaporPhoneNumber());
        return response;
    }
}

