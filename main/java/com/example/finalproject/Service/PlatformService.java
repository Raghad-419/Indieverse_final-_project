package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Platform;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.GameRepository;
import com.example.finalproject.Repository.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatformService {
    private final PlatformRepository platformRepository;
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;

    public void assignPlatformToGame (Integer userId, Integer gameId, Integer platformId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        if(myUser.isBanned())throw new ApiException("User is banned");

        Game game = gameRepository.findGameById(gameId);
        if (game == null) throw new ApiException("Game not found");


        Platform platform = platformRepository.findPlatformById(platformId);
        if (platform == null) throw new ApiException("Platform not found");

        game.getPlatforms().add(platform);
        platform.getGames().add(game);
        gameRepository.save(game);
        platformRepository.save(platform);

    }
}
