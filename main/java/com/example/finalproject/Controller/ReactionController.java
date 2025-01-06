package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reaction")
public class ReactionController {
    private final ReactionService reactionService;

    @PutMapping("/like/{reviewId}")
    public ResponseEntity likeReview(@AuthenticationPrincipal MyUser user,@PathVariable Integer reviewId){
        reactionService.likeReview(user.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Like added to review"));
    }


    @PutMapping("/dislike/{reviewId}")
    public ResponseEntity dislikeReview(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewId){
        reactionService.dislikeReview(user.getId(), reviewId);
        return ResponseEntity.status(200).body(new ApiResponse("Dislike added to review"));
    }

}
