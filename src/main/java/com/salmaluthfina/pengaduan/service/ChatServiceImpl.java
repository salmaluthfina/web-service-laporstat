/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.entity.ChatMessage;
import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import com.salmaluthfina.pengaduan.payload.ChatMessageRequest;
import com.salmaluthfina.pengaduan.payload.ChatMessageResponse;
import com.salmaluthfina.pengaduan.repository.ChatMessageRepository;
import com.salmaluthfina.pengaduan.repository.PengaduanRepository;
import com.salmaluthfina.pengaduan.repository.UserRepository;
import com.salmaluthfina.pengaduan.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private PengaduanRepository pengaduanRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private NotificationService notificationService;

    @Override
    public void sendMessage(String username, Long pengaduanId, ChatMessageRequest chatMessageRequest) {
        User sender = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found."));
        
        Pengaduan pengaduan = pengaduanRepository.findById(pengaduanId).orElseThrow(() ->
                new RuntimeException("Pengaduan not found."));
        
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setPengaduan(pengaduan);
        chatMessage.setSender(sender);
        chatMessage.setMessage(chatMessageRequest.getMessage());
        chatMessage.setTimestamp(LocalDateTime.now());

        chatMessageRepository.save(chatMessage);
        
        notificationService.processChatNotification("New message from admin kategori: " + chatMessageRequest.getMessage(), "PELAPOR", sender);
    }

    @Override
    public List<ChatMessageResponse> getChatHistory(Long pengaduanId) {
        return chatMessageRepository.findByPengaduanIdOrderByTimestampAsc(pengaduanId).stream()
                .map(message -> ChatMessageResponse.builder()
                        .sender(message.getSender().getUsername())  // Mengambil username dari sender
                        .message(message.getMessage())
                        .sentAt(message.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
    
}