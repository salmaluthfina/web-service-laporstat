/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.payload;

/**
 *
 * @author salmaluthfinaa
 */

import lombok.Data;

@Data
public class FeedbackResponse {

    private int keramahanRating;
    private int kualitasRating;
    private int kecepatanRating;
    private int kemudahanRating;
    private String message;
    private String pelaporName;
    private String pelaporEmail;
    private String pelaporPhoneNumber;
}

