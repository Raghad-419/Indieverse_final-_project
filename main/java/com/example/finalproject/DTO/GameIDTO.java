package com.example.finalproject.DTO;

import com.example.finalproject.Model.Genre;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameIDTO {

    @NotEmpty(message = "Empty name")
    private String name;

    @NotNull(message = "Empty balance")
    @PositiveOrZero(message = "Price must be positive")
    private Double price;

    @NotEmpty(message = "Empty name")
    private String size;

    private LocalDate releaseDate;

}
