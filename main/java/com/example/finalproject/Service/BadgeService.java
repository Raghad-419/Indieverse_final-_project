package com.example.finalproject.Service;

import com.example.finalproject.DTO.BadgeODTO;
import com.example.finalproject.Model.Badge;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Badge;
import com.example.finalproject.Repository.BadgeRepository;
import com.example.finalproject.Repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final GameRepository gameRepository;


    public void assignBadge() {
        List<Game> games = gameRepository.findAll();
            for (Game game : games) {

                if (game.getAnalytics().getTotalDownloads() > 2000){
                    game.getBadges().add(badgeRepository.findBadgeByBadgeId(1));
                }

                if (game.getAnalytics().getAverageRating() > 4.5){
                    game.getBadges().add(badgeRepository.findBadgeByBadgeId(2));
                }

                gameRepository.save(game);
            }
    }



}
