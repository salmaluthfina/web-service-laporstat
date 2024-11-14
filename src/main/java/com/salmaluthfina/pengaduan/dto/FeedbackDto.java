/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.dto;

/**
 *
 * @author salmaluthfinaa
 */

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    private Long id; // ID Feedback
    
    private Long pengaduId; // ID Pengadu (yang memberikan feedback)
    
    private Long pengaduanId; // ID Pengaduan yang diberi feedback
    
    @NotNull(message = "Harap mengisikan rating untuk feedback.")
    private Integer rating; // Rating (misalnya 1-5)
    
    private String komentar; // Komentar atau umpan balik dari pengadu
}

