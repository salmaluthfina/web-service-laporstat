/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.controller;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.exception.InvalidActionException;
import com.salmaluthfina.pengaduan.payload.FeedbackRequest;
import com.salmaluthfina.pengaduan.payload.FeedbackResponse;
import com.salmaluthfina.pengaduan.service.FeedbackService;
import com.salmaluthfina.pengaduan.service.PengaduanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Feedback", description = "API untuk mengelola feedback dari pengguna")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    
    @Autowired
    private PengaduanService pengaduanService;
    
    @PostMapping("/{pengaduanId}")
    public FeedbackResponse createFeedback(
            @PathVariable Long pengaduanId,
            @RequestBody FeedbackRequest feedbackRequest,
            Authentication authentication) {

        String username = authentication.getName();

        if (!pengaduanService.canSubmitFeedback(pengaduanId)) {
            throw new InvalidActionException("Feedback can only be submitted for completed tickets.");
        }

        return feedbackService.createFeedback(pengaduanId, feedbackRequest, username);
    }

    @GetMapping("/{pengaduanId}")
    public FeedbackResponse getFeedback(@PathVariable Long pengaduanId) {
        return feedbackService.getFeedbackByPengaduanId(pengaduanId);
    }
}
