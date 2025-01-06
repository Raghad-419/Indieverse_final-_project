package com.example.finalproject.Repository;

import com.example.finalproject.Model.Reviewer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    Reviewer findReviewerByReviewerId(Integer reviewerId);

    List<Reviewer> findReviewersByValidated(boolean validated);
}
