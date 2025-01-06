package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewerODTO {

    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String profileUrl;
    private String bio;

}
