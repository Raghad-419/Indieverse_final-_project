package com.example.finalproject.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class ReviewerIDTO {

    @NotEmpty(message = "Username is required")
    private String username;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$",message = "Password must contain at least one digit [0-9].Password must contain at least one lowercase Latin character [a-z].Password must contain at least one uppercase Latin character [A-Z].Password must contain at least one special character like ! @ # & ( ).Password must contain a length of at least 8 characters and a maximum of 20 characters.")
    private String password;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^05[0-9]{8}$",message = "Phone number must be 10 digits long starting with 05")
    private String phoneNumber;

    @NotEmpty(message = "Profile URL is required")
    @URL(message = "Please enter a valid URL")
    private String profileUrl;

    @Size(max = 300, message = "Bio should not exceed 300 characters")
    private String bio;
}
