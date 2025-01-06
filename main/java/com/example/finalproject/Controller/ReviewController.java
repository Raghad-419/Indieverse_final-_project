package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.ReviewIDTO;
import com.example.finalproject.DTO.ReviewODTO;
import com.example.finalproject.DTO.VideoReviewIDTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Service.ReviewService;
import com.example.finalproject.Service.VideoReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final VideoReviewService videoReviewService;

    @GetMapping("/get-reviews")
    public ResponseEntity<List<ReviewODTO>> getMyReviews(@AuthenticationPrincipal MyUser user){
       return ResponseEntity.status(200).body(reviewService.getMyReviews(user.getId()));
    }

    @GetMapping("/get-all-reviews")
    public ResponseEntity<List<ReviewODTO>> getAllReviews(){
        return ResponseEntity.status(200).body(reviewService.getAllReviews());
    }


    @GetMapping("/get-review-by-game/{gameId}")
    public ResponseEntity getReviewsByGame(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        return ResponseEntity.status(200).body(reviewService.getReviewsByGame(user.getId(), gameId));
    }


    @GetMapping("/get-review-by-reviewers/{gameId}")
    public ResponseEntity getReviewsByGameForReviewers(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        return ResponseEntity.status(200).body(reviewService.getReviewsForGameByReviewers(user.getId(), gameId));
    }

    @GetMapping("/get-review-by-players/{gameId}")
    public ResponseEntity getReviewsForGameByPlayers(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        return ResponseEntity.status(200).body(reviewService.getReviewsForGameByPlayers(user.getId(), gameId));
    }


    @PostMapping("/add-review/{gameId}")
    public ResponseEntity<ApiResponse> addReview(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId, @RequestBody ReviewIDTO reviewIDTO){
        reviewService.addReview(user.getId(), gameId,reviewIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    @PutMapping("/update-review/{reviewId}")
    public ResponseEntity<ApiResponse> updateReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewId , @RequestBody ReviewIDTO reviewIDTO){
        reviewService.updateReview(user.getId(), reviewId,reviewIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewId){
        reviewService.deleteReview(user.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }

    @PostMapping("/upload-video-review/{gameId}")
    public ResponseEntity<String> uploadVideoReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId,@RequestBody @Valid VideoReviewIDTO videoReviewIDTO) {
        videoReviewService.uploadVideoReview(user.getId(), gameId,videoReviewIDTO);
        return ResponseEntity.status(200).body("Video review uploaded successfully");
    }




}
