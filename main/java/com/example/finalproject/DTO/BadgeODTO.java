package com.example.finalproject.DTO;

import com.example.finalproject.Model.Game;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeODTO {

    private String name;
    private String description;

    private Set<Game> games;

}
