package com.example.finalproject.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerODTO {
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer gamesPurchased;

}
