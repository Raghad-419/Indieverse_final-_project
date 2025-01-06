package com.example.finalproject.Repository;

import com.example.finalproject.Model.Analytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {
    Analytics findAnalyticsById(Integer id);

    Analytics findAnalyticsByGame_Id(Integer gameId);

}
