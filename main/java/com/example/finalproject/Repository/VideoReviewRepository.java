package com.example.finalproject.Repository;

import com.example.finalproject.Model.VideoReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoReviewRepository extends JpaRepository<VideoReview, Integer> {

}
