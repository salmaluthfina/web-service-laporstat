/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.service;

/**
 *
 * @author salmaluthfinaa
 */

import com.salmaluthfina.pengaduan.payload.ChatMessageRequest;
import com.salmaluthfina.pengaduan.payload.ChatMessageResponse;

import java.util.List;

public interface ChatService {
    void sendMessage(String username, Long pengaduanId, ChatMessageRequest chatMessageRequest);
    List<ChatMessageResponse> getChatHistory(Long pengaduanId);
}
