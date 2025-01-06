package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {
    private final ReviewRepository reviewRepository;
    private final ReactionRepository reactionRepository;
    private final AuthRepository authRepository ;
    private final PlayerRepository playerRepository ;
    private final ReviewerRepository reviewerRepository;


    //Raghad
    public void likeReview(Integer userId, Integer reviewId) {
        Review review = reviewRepository.findReviewByReviewId(reviewId);
                if(review ==null){throw new ApiException("Review not found");}
        MyUser myUser= authRepository.findMyUserById(userId);
                if(myUser==null){ throw new ApiException("User not found");}

        // Check if user is a Player or Reviewer
        Player player = playerRepository.findById(userId).orElse(null);
        Reviewer reviewer = reviewerRepository.findById(userId).orElse(null);

        if (player == null && reviewer == null) {
            throw new RuntimeException("User not found as either a Player or a Reviewer");
        }

        // Check if reaction already exists
        Reaction existingReaction = findReaction(userId, player != null, reviewId);

        if (existingReaction != null && existingReaction.getReactionType() == 1) {
            throw new RuntimeException("You have already liked this review");
        }

        if (existingReaction != null && existingReaction.getReactionType() == 0) {
            review.setDislikes(review.getDislikes() - 1);
        }

        Reaction reaction = existingReaction != null ? existingReaction : new Reaction();
        if (player != null) {
            reaction.setPlayer(player);
        } else {
            reaction.setReviewer(reviewer);
        }
        reaction.setReview(review);
        reaction.setReactionType(1);

        review.setLikes(review.getLikes() + 1);
        reviewRepository.save(review);
        reactionRepository.save(reaction);
    }

    //Raghad
    public void dislikeReview(Integer userId, Integer reviewId) {
        Review review = reviewRepository.findReviewByReviewId(reviewId);
        if(review ==null){throw new ApiException("Review not found");}

        MyUser myUser= authRepository.findMyUserById(userId);
        if(myUser==null){ throw new ApiException("User not found");}

        // Check if user is a Player or Reviewer
        Player player = playerRepository.findById(userId).orElse(null);
        Reviewer reviewer = reviewerRepository.findById(userId).orElse(null);

        if (player == null && reviewer == null) {
            throw new RuntimeException("User not found as either a Player or a Reviewer");
        }

        // Check if reaction already exists
        Reaction existingReaction = findReaction(userId, player != null, reviewId);

        if (existingReaction != null && existingReaction.getReactionType() == 0) {
            throw new RuntimeException("You have already disliked this review");
        }

        if (existingReaction != null && existingReaction.getReactionType() == 1) {
            review.setLikes(review.getLikes() - 1);
        }

        Reaction reaction = existingReaction != null ? existingReaction : new Reaction();
        if (player != null) {
            reaction.setPlayer(player);
        } else {
            reaction.setReviewer(reviewer);
        }
        reaction.setReview(review);
        reaction.setReactionType(0);

        review.setDislikes(review.getDislikes() + 1);
        reviewRepository.save(review);
        reactionRepository.save(reaction);
    }


    private Reaction findReaction(Integer userId, boolean isPlayer, Integer reviewId) {
        if (isPlayer) {
            return reactionRepository.findReactionByPlayerIdAndReviewReviewId(userId, reviewId);
        } else {
            return reactionRepository.findReactionByPlayerIdAndReviewReviewId(userId, reviewId);
        }
    }

}
