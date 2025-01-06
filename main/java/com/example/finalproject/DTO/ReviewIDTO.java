package com.example.finalproject.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewIDTO {

    @NotEmpty(message = "Review text is required")
    @Size(max = 300,message = "Review text should not exceed 300 characters")
    private String reviewText;

    @Min(value = 1,message = "Rating can't be less than 1")
    @Max(value = 5,message = "Rating can't be more than 5")
    private Integer rating;
}
