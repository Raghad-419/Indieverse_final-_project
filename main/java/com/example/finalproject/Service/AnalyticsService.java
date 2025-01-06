package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.AnalyticsODTO;
import com.example.finalproject.Model.Analytics;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.AnalyticsRepository;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.GameRepository;
import com.example.finalproject.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;
    private final TransactionService transactionService;
    private final ReviewService reviewService;

    public AnalyticsODTO getAnalyticsForGame(Integer userId, Integer gameId){
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null)throw new ApiException("User not found");

        if(user.isBanned())throw new ApiException("User is banned");

        Analytics analytics = analyticsRepository.findAnalyticsByGame_Id(gameId);
        if(analytics == null)throw new ApiException("analytics not found");

        return convertToODTO(analytics);
    }

    @Scheduled(cron = "*/59 * * * * *")
    public void updateAnalyticsForGames(){
        List<Game> games = gameRepository.findAll();
        for(Game game : games){
            if (game.getAnalytics() == null) {
                game.setAnalytics(new Analytics());
            }

            game.getAnalytics().setTotalEarnings(transactionService.calculateTotalEarnings(game.getId()));
            game.getAnalytics().setAverageRating(reviewService.calculateAverageRating(game.getId()));
            gameRepository.save(game);
        }
    }

    public AnalyticsODTO convertToODTO(Analytics analytics) {
        return new AnalyticsODTO(analytics.getTotalDownloads(),analytics.getActivePlayers(),analytics.getTotalEarnings(),analytics.getAverageRating());

    }

}
