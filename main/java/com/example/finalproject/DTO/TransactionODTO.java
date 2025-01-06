package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionODTO {

    private String orderNumber;
    private LocalDateTime transactionDate;
    private Double amount;
    private String status;
    private String activationCode;
}
