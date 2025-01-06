package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.DeveloperIDTO;
import com.example.finalproject.DTO.ReviewerIDTO;
import com.example.finalproject.DTO.ReviewerODTO;
import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Reviewer;
import com.example.finalproject.Service.ReviewerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviewer")
public class ReviewerController {
    private final ReviewerService reviewerService;

    @GetMapping("/get-reviewer")
    public ResponseEntity<ReviewerODTO> getMyReviewer(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(reviewerService.getMyReviewer(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid ReviewerIDTO reviewerIDTO) {
        reviewerService.register(reviewerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateReviewer(@AuthenticationPrincipal MyUser user, @RequestBody @Valid ReviewerIDTO reviewerIDTO) {
        reviewerService.updateReviewer(user.getId(), reviewerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteReviewer(@AuthenticationPrincipal MyUser user) {
        reviewerService.deleteReviewer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted"));
    }


}
