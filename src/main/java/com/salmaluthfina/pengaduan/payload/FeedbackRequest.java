/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.payload;

/**
 *
 * @author salmaluthfinaa
 */

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackRequest {

    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 5, message = "Rating maximum is 5")
    private int keramahanRating;

    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 5, message = "Rating maximum is 5")
    private int kualitasRating;

    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 5, message = "Rating maximum is 5")
    private int kecepatanRating;

    @Min(value = 1, message = "Rating minimum is 1")
    @Max(value = 5, message = "Rating maximum is 5")
    private int kemudahanRating;

    @NotBlank(message = "Message is required")
    @Size(max = 150, message = "Message must be at most 150 characters")
    private String message;
}
