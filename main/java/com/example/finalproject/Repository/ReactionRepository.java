package com.example.finalproject.Repository;

import com.example.finalproject.Model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {

Reaction findReactionByPlayerIdAndReviewReviewId(Integer userId,Integer reviewId);
}
