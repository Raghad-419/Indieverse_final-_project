package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.ReviewIDTO;
import com.example.finalproject.DTO.ReviewODTO;
import com.example.finalproject.DTO.ReviewerIDTO;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;
    private final ReviewerRepository reviewerRepository;
    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    public List<ReviewODTO> getMyReviews(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        return convertReviewListToReviewODTO(reviewRepository.findReviewsByPlayer_Id(userId));
    }

    public List<ReviewODTO> getAllReviews() {
        List<Review> reviews = (List<Review>) reviewRepository.findAll();

        return convertReviewListToReviewODTO(reviews);
    }
    //Raghad
    public List<ReviewODTO> getReviewsByGame(Integer userId, Integer gameId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiException("Game not found"));

        return convertReviewListToReviewODTO(reviewRepository.findReviewsByGame(game));
    }

    //Raghad
    public List<ReviewODTO> getReviewsForGameByReviewers(Integer userId, Integer gameId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiException("Game not found"));

        // Fetch only reviews written by reviewers for the specified game
        return convertReviewListToReviewODTO(reviewRepository.findAllByGameAndReviewerIsNotNull(game));
    }

    //Raghad
    public List<ReviewODTO> getReviewsForGameByPlayers(Integer userId, Integer gameId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiException("Game not found"));

        // Fetch only reviews written by players for the specified game
        return convertReviewListToReviewODTO(reviewRepository.findAllByGameAndPlayerIsNotNull(game));
    }

    //Raghad
    public Double calculateAverageRating(Integer gameId) {
        // Fetch the game details
        Game game = gameRepository.findGameById(gameId);
        if(game==null){throw new ApiException("Game not found");}

        // Get the game's reviews
        Set<Review> reviews = game.getReviews();

        // Check if there are reviews
        if (reviews.isEmpty()) {
            return 0.0; // Return 0 if no reviews exist
        }

        // Calculate the average rating
        double averageRating = reviews.stream()
                .mapToInt(Review::getRating) // Extract ratings
                .average()                   // Calculate average
                .orElse(0.0);                // Default to 0.0 if no values

        return averageRating;
    }

    public void addReview(Integer userId,Integer gameId,ReviewIDTO reviewIDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        if(myUser.isBanned())throw new ApiException("User is banned");

        Game game = gameRepository.findGameById(gameId);
        if(game == null){
            throw new ApiException("Game not found");
        }

        Player player = playerRepository.findById(userId).orElse(null);
        Reviewer reviewer = reviewerRepository.findById(userId).orElse(null);

        if (player == null && reviewer == null) {
            throw new ApiException("User must be a Player or a Reviewer to leave a review");
        }

        if (player != null) {
            boolean hasPurchased = transactionRepository.existsByPlayerAndGame(player, game);
            if (!hasPurchased) {
                throw new ApiException("Player must purchase the game before leaving a review");
            }
        }

        if (reviewer != null) {
            boolean isAssigned = transactionRepository.existsByReviewerAndGame(reviewer, game);
            if (!isAssigned) {
                throw new ApiException("Reviewer must be assigned to review the game");
            }
        }

        boolean reviewExists = reviewRepository.existsByGameAndPlayerOrReviewer(game, player, reviewer);
        if (reviewExists) {
            throw new ApiException("You have already reviewed this game");
        }

        if (player == null) if (!reviewer.isValidated()) throw new ApiException("reviewer is not validated");

        Review review = convertReviewIDTOToReview(reviewIDTO);
        review.setReviewDate(LocalDateTime.now());
        review.setGame(game);
        if (player != null) {
            review.setPlayer(player);
        } else {
            review.setReviewer(reviewer);
        }

        reviewRepository.save(review);
    }

    public void updateReview(Integer userId,Integer reviewId, ReviewIDTO reviewIDTO) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        if(myUser.isBanned())throw new ApiException("User is banned");

        Review oldReview = reviewRepository.findReviewByReviewId(reviewId);
        if (oldReview == null) throw new ApiException("review not found");

        if (oldReview.getPlayer() != null) {
            if (!oldReview.getPlayer().equals(myUser.getPlayer())) throw new ApiException("player is not the review creator");
        }

        if (oldReview.getReviewer() != null) {
            if (!oldReview.getReviewer().equals(myUser.getReviewer())) throw new ApiException("reviewer is not the review creator");
        }

        oldReview.setReviewText(reviewIDTO.getReviewText());
        oldReview.setRating(reviewIDTO.getRating());

        reviewRepository.save(oldReview);
    }

    public void deleteReview(Integer userId, Integer reviewId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        if(myUser.isBanned())throw new ApiException("User is banned");

        Review review = reviewRepository.findReviewByReviewId(reviewId);

        if (review.getPlayer() != null) {
            if (!review.getPlayer().equals(myUser.getPlayer())) throw new ApiException("player is not the review creator");
        }

        if (review.getReviewer() != null) {
            if (!review.getReviewer().equals(myUser.getReviewer())) throw new ApiException("reviewer is not the review creator");
        }

        reviewRepository.delete(review);
    }


    public Review convertReviewIDTOToReview(ReviewIDTO reviewIDTO) {
        Review review = new Review();

        review.setReviewText(reviewIDTO.getReviewText());
        review.setRating(reviewIDTO.getRating());

        return review;
    }

    public ReviewODTO convertReviewToReviewODTO(Review review) {
        return new ReviewODTO(review.getReviewText(), review.getRating(), review.getReviewDate(),review.getLikes(),review.getDislikes());
    }

    public List<ReviewODTO> convertReviewListToReviewODTO(List<Review> reviewList) {
        List<ReviewODTO> reviewODTOList = new ArrayList<>();
        for (Review review : reviewList) {
            reviewODTOList.add(convertReviewToReviewODTO(review));
        }
        return reviewODTOList;
    }


}
