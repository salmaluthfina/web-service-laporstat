/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.mapper;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.dto.ChatDto;
import com.salmaluthfina.pengaduan.entity.ChatMessage;
import com.salmaluthfina.pengaduan.entity.Pengaduan;
import com.salmaluthfina.pengaduan.entity.User;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatDto toDto(ChatMessage chatMessage) {
        return ChatDto.builder()
                .sender(chatMessage.getSender().getUsername())
                .message(chatMessage.getMessage())
                .timestamp(chatMessage.getTimestamp())
                .build();
    }

    public ChatMessage toEntity(ChatDto chatDto, User sender, Pengaduan pengaduan) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setPengaduan(pengaduan);
        chatMessage.setMessage(chatDto.getMessage());
        chatMessage.setTimestamp(chatDto.getTimestamp() != null ? chatDto.getTimestamp() : LocalDateTime.now());
        return chatMessage;
    }
}
