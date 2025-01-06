package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestODTO {

    private String sender;
    private String receiver;
    private String type;
    private LocalDateTime requestDate;
    private String status;
}
