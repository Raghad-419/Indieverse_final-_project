package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.GameIDTO;
import com.example.finalproject.DTO.GameODTO;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.EngineService;
import com.example.finalproject.Service.GameService;
import com.example.finalproject.Service.PlatformService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game")
public class GameController {
    private final GameService gameService;
    private final EngineService engineService;
    private final PlatformService platformService;

    @GetMapping("/get-all-games")
    public ResponseEntity getAllGames(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(gameService.getAllGames(user.getId()));
    }

    @PostMapping("/add-game")
    public ResponseEntity addGame(@AuthenticationPrincipal MyUser user,@RequestBody @Valid GameIDTO gameIDTO){
        gameService.addGame(user.getId(), gameIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Game added"));
    }

    @PutMapping("/update-game/{gameId}")
    public ResponseEntity updateGame(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId ,@RequestBody @Valid GameIDTO gameIDTO){
        gameService.updateGame(user.getId(), gameId,gameIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Game updated"));
    }

    @DeleteMapping("/delete-game/{gameId}")
    public ResponseEntity deleteGame(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId){
        gameService.deleteGame(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Game deleted"));
    }

    @GetMapping("/get-games-by-badge/{badgeId}")
    public ResponseEntity findGamesByBadge(@AuthenticationPrincipal MyUser user, @PathVariable Integer badgeId){
        return ResponseEntity.status(200).body(gameService.findGamesByBadge(user.getId(), badgeId));
    }

    @GetMapping("/recommend-games")
    public ResponseEntity recommendGames(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(gameService.recommendGames(user.getId()));
    }

    @GetMapping("/filter-games-by-price-range/{minPrice}/{maxPrice}")
    public ResponseEntity filterGamesByPriceRange(@AuthenticationPrincipal MyUser user, @PathVariable  Double minPrice,@PathVariable Double maxPrice){
        return ResponseEntity.status(200).body(gameService.filterGamesByPriceRange(user.getId(), minPrice, maxPrice));
    }

    @GetMapping("/find-game-by-developer")
    public ResponseEntity findGamesByDeveloperId(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(gameService.findGamesByDeveloperId(user.getId()));
    }


    @PutMapping("/apply-discount/{gameId}/{discount}")
    public ResponseEntity applyDiscount(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId, @PathVariable Double discount){
        gameService.applyDiscount(user.getId(), gameId,discount);
        return ResponseEntity.status(200).body(new ApiResponse("Discount applied"));
    }

    @PutMapping("/remove-discount/{gameId}")
    public ResponseEntity removeDiscount(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId){
        gameService.removeDiscount(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Discount removed"));
    }

    @GetMapping("/find-similar-games/{gameId}")
    public ResponseEntity findSimilarGames(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId){
        return ResponseEntity.status(200).body(gameService.findSimilarGames(user.getId(), gameId));
    }

    @PutMapping("/assign-tag/{gameId}/{tagId}")
    public ResponseEntity assignTagToGame(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId, @PathVariable Integer tagId){
        gameService.assignTagToGame(user.getId(), gameId,tagId);
        return ResponseEntity.status(200).body(new ApiResponse("Tag assigned"));
    }

    @PutMapping("/assign-genre/{gameId}/{genreId}")
    public ResponseEntity assignGenreToGame(@AuthenticationPrincipal MyUser user,@PathVariable Integer gameId, @PathVariable Integer genreId){
        gameService.assignGenreToGame(user.getId(), gameId,genreId);
        return ResponseEntity.status(200).body(new ApiResponse("Genre assigned"));
    }



    @GetMapping("/get-top-games/{limit}")
    public ResponseEntity getTopRatedGames(@AuthenticationPrincipal MyUser user, @PathVariable Integer limit){
        return ResponseEntity.status(200).body(gameService.getTopRatedGames(user.getId(), limit));
    }

    @GetMapping("/released-in-range")
    public ResponseEntity getGamesReleasedInRange( @AuthenticationPrincipal MyUser user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<GameODTO> games = gameService.getGamesByReleaseDateRange(user.getId(), startDate, endDate);
        return ResponseEntity.ok(games);
    }

    @GetMapping("/recent-release-games/{days}")
    public ResponseEntity findRecentlyReleasedGames(@AuthenticationPrincipal MyUser user, @PathVariable Integer days){
        return ResponseEntity.status(200).body(gameService.findRecentlyReleasedGames(user.getId(), days));
    }

    @PutMapping("/assign-engine/{developerId}/{engineId}")
    public ResponseEntity assignEngineToDeveloper(@AuthenticationPrincipal MyUser user, @PathVariable Integer developerId , @PathVariable Integer engineId){
        engineService.assignEngineToDeveloper(user.getId(), developerId, engineId);
        return ResponseEntity.status(200).body(new ApiResponse("Engine assigned"));
    }

    @PutMapping("/assign-platform/{gameId}/{platformId}")
    public ResponseEntity assignPlatformToGame(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId, @PathVariable Integer platformId){
        platformService.assignPlatformToGame(user.getId(), gameId, platformId);
        return ResponseEntity.status(200).body(new ApiResponse("Platform assigned"));
    }

}
