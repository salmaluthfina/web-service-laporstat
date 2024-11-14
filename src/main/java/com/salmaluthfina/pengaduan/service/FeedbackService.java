/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.FeedbackRequest;
import com.salmaluthfina.pengaduan.payload.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse createFeedback(Long pengaduanId, FeedbackRequest feedbackRequest, String username);
    FeedbackResponse getFeedbackByPengaduanId(Long pengaduanId);
}

