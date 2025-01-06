package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsODTO {

    private Integer totalDownloads;
    private Integer activePlayers;
    private Double totalEarnings;
    private Double averageRating;
}
