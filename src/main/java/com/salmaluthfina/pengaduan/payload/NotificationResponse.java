/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.payload;

/**
 *
 * @author salmaluthfinaa
 */

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationResponse {
    private Long id;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;
    private String type;
    
    public NotificationResponse(String message, LocalDateTime timestamp, String type) {
        this.message = message;
        this.timestamp = timestamp;
        this.type = type;
    }
}
