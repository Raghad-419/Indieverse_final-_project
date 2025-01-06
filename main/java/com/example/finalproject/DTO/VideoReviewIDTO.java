package com.example.finalproject.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoReviewIDTO {

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "description is required")
    @Size(max = 300, message = "description should not exceed 300 characters")
    private String description;

    @NotEmpty(message = "video path is required")
    private String videoPath;
}
