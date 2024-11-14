/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.salmaluthfina.pengaduan.entity;

/**
 *
 * @author salmaluthfinaa
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pengaduan_id", nullable = false)
    private Pengaduan pengaduan;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    private String pelaporName;
    private String pelaporEmail;
    private String pelaporPhoneNumber;
}
