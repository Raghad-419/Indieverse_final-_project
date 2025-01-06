package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SupportTicketODTO {

    private String title;
    private String description;
    private String status;
    private LocalDateTime creationDate;

}
