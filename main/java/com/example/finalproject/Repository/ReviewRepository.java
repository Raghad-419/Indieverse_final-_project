package com.example.finalproject.Repository;

import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Model.Reviewer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Review findReviewByReviewId(Integer reviewId);

    List<Review> findReviewByGameId(Integer gameId);

    boolean existsByGameAndPlayerOrReviewer(Game game, Player player, Reviewer reviewer);



    List<Review> findReviewsByPlayer_Id(Integer playerId);

    List<Review> findReviewsByGame(Game game);

    List<Review> findAllByGameAndReviewerIsNotNull(Game game);

    List<Review> findAllByGameAndPlayerIsNotNull(Game game);
}
