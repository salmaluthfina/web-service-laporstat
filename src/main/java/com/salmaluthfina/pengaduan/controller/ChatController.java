/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.controller;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.ChatMessageRequest;
import com.salmaluthfina.pengaduan.payload.ChatMessageResponse;
import com.salmaluthfina.pengaduan.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat Message", description = "API untuk mengelola pesan antara pengguna dengan admin kategori")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{pengaduanId}")
    public ResponseEntity<String> sendMessage(@PathVariable Long pengaduanId, @RequestBody ChatMessageRequest messageRequest, Authentication authentication) {
        String username = authentication.getName();
        chatService.sendMessage(username, pengaduanId, messageRequest);
        return ResponseEntity.ok("Message sent successfully.");
    }

    @GetMapping("/{pengaduanId}")
    public ResponseEntity<List<ChatMessageResponse>> getChatHistory(@PathVariable Long pengaduanId) {
        List<ChatMessageResponse> messages = chatService.getChatHistory(pengaduanId);
        return ResponseEntity.ok(messages);
    }
}