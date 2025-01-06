package com.example.finalproject.Repository;

import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Integer> {
    Developer findDeveloperById(Integer id);


    @Query("SELECT d FROM Developer d " + "LEFT JOIN d.games g " + "GROUP BY d.id " + "ORDER BY COUNT(g.id) DESC")
    List<Developer> findTopDevelopersByGameCount();

    List<Developer> findDevelopersByValidated(boolean validated);






}
