package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewODTO {

    private String reviewText;
    private Integer rating;
    private LocalDateTime reviewDate;
    private Integer likes;
    private Integer dislikes;

}
