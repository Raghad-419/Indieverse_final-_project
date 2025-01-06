package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.GameIDTO;
import com.example.finalproject.DTO.GameODTO;
import com.example.finalproject.DTO.PlayerIDTO;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final DeveloperRepository developerRepository;
    private final AuthRepository authRepository;
    private final TagRepository tagRepository;
    private final GenreRepository genreRepository;
    private final RequestRepository requestRepository;
    private final PlayerRepository playerRepository;



    public List<GameODTO> getAllGames(Integer userId){
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        return convertGameToGameODTO(gameRepository.findAll());
    }

    public void addGame(Integer developerId, GameIDTO gameIDTO){
        Developer developer = developerRepository.findDeveloperById(developerId);
        if(developer ==null)throw new ApiException("Developer not found");

        if(developer.getMyUser().isBanned())throw new ApiException("User is banned");
        if(!developer.isValidated())throw new ApiException("Developer is not validated");


        Game game =convertGameIDToToGame(gameIDTO);
        if(gameRepository.findGamesByDeveloper(developer).contains(game))throw new ApiException("This game is already exist");

        game.setDeveloper(developer);
        gameRepository.save(game);

        Request request = new Request(null,developerId,1,"VALIDATION",LocalDateTime.now(),"PENDING",developer,null,null,game,null);
        requestRepository.save(request);


    }

    public void updateGame(Integer developerId, Integer gameId ,GameIDTO gameIDTO){
        Developer developer = developerRepository.findDeveloperById(developerId);
        if(developer==null){
            throw new ApiException("Developer not found");}

        MyUser myUser =authRepository.findMyUserById(developerId);
        if(myUser ==null){
            throw new ApiException("User not found");
        }

        if(myUser.isBanned())throw new ApiException("User is banned");


        Game oldGame =gameRepository.findGameById(gameId);
        if(oldGame ==null){
            throw new ApiException("Game not found");
        }

        if(!Objects.equals(oldGame.getDeveloper().getId(), myUser.getId())){
            throw new ApiException("This is not your game");
        }

        oldGame.setName(gameIDTO.getName());
        oldGame.setPrice(gameIDTO.getPrice());
        oldGame.setSize(gameIDTO.getSize());
        gameRepository.save(oldGame);
    }

    public void deleteGame(Integer developerId, Integer gameId){
        Developer developer = developerRepository.findDeveloperById(developerId);
        MyUser myUser = authRepository.findMyUserById(developerId);
        if(developer ==null || myUser ==null){
            throw new ApiException("Developer not found");
        }

        if(myUser.isBanned())throw new ApiException("User is banned");

        Game game = gameRepository.findGameById(gameId);
        if(game ==null){
            throw new ApiException("Game not found");
        }
        gameRepository.delete(game);
    }



    //Raghad 1
    public List<GameODTO> findGamesByBadge(Integer userId,Integer badgeId) {
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("User not found");

        return convertGameToGameODTO(gameRepository.findGamesByBadgeId(badgeId));

    }

    //Raghad 2
    public List<GameODTO> recommendGames (Integer playerId){
            // Fetch the player's details
            Player player = playerRepository.findPlayerById(playerId);
            if (player == null) {
                throw new ApiException("Player not found");
            }

            Wishlist wishlist = player.getWishList();

            if (wishlist == null) {
                wishlist = new Wishlist();
                player.setWishList(wishlist);
                wishlist.setPlayer(player);
            }

            // Get the player's wishlist games
            Set<Game> wishlistGames = wishlist.getGames();

            // Get the player's purchased games
            Set<Game> purchasedGames = player.getGames();

            // Get the player's favorite genres based on purchased games
            Set<Genre> favoriteGenres = purchasedGames.stream()
                    .map(Game::getGenre)
                    .collect(Collectors.toSet());

            if (wishlistGames == null && purchasedGames != null) {
                List<Game> recommendedGames = gameRepository.findByGenreIn(favoriteGenres).stream()
                        .filter(game -> !purchasedGames.contains(game)).sorted(Comparator.comparing(Game::getReleaseDate).reversed()).toList();
                return convertGameToGameODTO(recommendedGames);

            }else if(purchasedGames == null && wishlistGames == null) {
                // Recommend games from the same genres, excluding already purchased or wishlisted games
                List<Game> recommendedGames = gameRepository.findGamesByBadgeId(1);
                return convertGameToGameODTO(recommendedGames);
            }else if(wishlistGames != null && purchasedGames == null) {
                List<Game> recommendedGames = gameRepository.findByGenreIn(favoriteGenres).stream()
                        .filter(game -> !wishlistGames.contains(game)).sorted(Comparator.comparing(Game::getReleaseDate).reversed()).toList();
                return convertGameToGameODTO(recommendedGames);
            }else {
                List<Game> recommendedGames = gameRepository.findByGenreIn(favoriteGenres).stream()
                        .filter(game -> !purchasedGames.contains(game) && !wishlistGames.contains(game)).sorted(Comparator.comparing(Game::getReleaseDate).reversed()).toList();
                return convertGameToGameODTO(recommendedGames);
            }
    }

    //Raghad 3
    public List<GameODTO> filterGamesByPriceRange (Integer userId, Double minPrice, Double maxPrice){
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("User not found");

        return convertGameToGameODTO(gameRepository.findByPriceBetween(minPrice, maxPrice));
    }

    //Raghad 4
    public List<GameODTO> findGamesByDeveloperId (Integer developerId){
        Developer developer = developerRepository.findDeveloperById(developerId);
        if (developer == null) throw new ApiException("Developer not found");

        return convertGameToGameODTO(gameRepository.findAllByDeveloper_Id(developerId));
        }


    //Raghad 5
    public List<GameODTO> getTopRatedGames (Integer userId ,Integer limit){
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("User not found");


            List<Game> games = gameRepository.findAll();

            List<Game> sortedGames = games.stream()
                    .sorted((g1, g2) -> Double.compare(
                            g2.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0),
                            g1.getReviews().stream().mapToInt(Review::getRating).average().orElse(0.0)
                    ))
                    .limit(limit)
                    .collect(Collectors.toList());

            return convertGameToGameODTO(sortedGames);
        }

    //Raghad 6
    public List<GameODTO> getGamesByReleaseDateRange (Integer userId, LocalDate startDate, LocalDate endDate){
            MyUser user = authRepository.findMyUserById(userId);
            if(user == null) throw new ApiException("User not found");

            return convertGameToGameODTO(gameRepository.findByReleaseDateBetween(startDate, endDate));
        }

 
    //Raghad 8
    public List<GameODTO> findRecentlyReleasedGames(Integer UserId, Integer days) {
        MyUser user = authRepository.findMyUserById(UserId);
        if(user == null) throw new ApiException("User not found");

        LocalDate startDate = LocalDate.now().minusDays(days);

        return convertGameToGameODTO(gameRepository.findByReleaseDateAfter(startDate));
        }


    public void applyDiscount(Integer userId, Integer gameId, Double discountPercentage) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if(myUser==null)throw new ApiException("User not found");

        Game game = gameRepository.findGameById(gameId);

        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new ApiException("Discount must be between 0 and 100");
        }

        if (game.getOriginalPrice() == null) {
            game.setOriginalPrice(game.getPrice());
        }

        game.setPrice(game.getPrice() * (1 - discountPercentage / 100));
        gameRepository.save(game);
    }

        public void removeDiscount (Integer userId, Integer gameId){
            MyUser myUser = authRepository.findMyUserById(userId);
            if (myUser == null) throw new ApiException("User not found");

            if(myUser.isBanned())throw new ApiException("User is banned");


            Game game = gameRepository.findGameById(gameId);
            if (game == null) throw new ApiException("Game not found");

            if (game.getOriginalPrice() == null) {
                throw new ApiException("No discount applied to this game");
            }

            game.setPrice(game.getOriginalPrice());
            game.setOriginalPrice(null);
            gameRepository.save(game);
        }

        public List<GameODTO> findSimilarGames (Integer userId, Integer gameId){
            MyUser myUser = authRepository.findMyUserById(userId);
            if (myUser == null) throw new ApiException("User not found");

            Game game = gameRepository.findGameById(gameId);
            if (game == null) throw new ApiException("Game not found");

            return convertGameToGameODTO(gameRepository.findGamesByGenre(game.getGenre()));
        }

        public void assignTagToGame (Integer userId, Integer gameId, Integer tagId){
            MyUser myUser = authRepository.findMyUserById(userId);
            if (myUser == null) throw new ApiException("User not found");

            if(myUser.isBanned())throw new ApiException("User is banned");


            Game game = gameRepository.findGameById(gameId);
            if (game == null) throw new ApiException("Game not found");

            Tag tag = tagRepository.findTagById(tagId);
            if (tag == null) throw new ApiException("Tag not found");

            game.getTags().add(tag);
            tag.getGames().add(game);

            tagRepository.save(tag);
            gameRepository.save(game);
        }

        public void assignGenreToGame (Integer userId, Integer gameId, Integer genreId){
            MyUser myUser = authRepository.findMyUserById(userId);
            if (myUser == null) throw new ApiException("User not found");

            if(myUser.isBanned())throw new ApiException("User is banned");


            Game game = gameRepository.findGameById(gameId);
            if (game == null) throw new ApiException("Game not found");

            Genre genre = genreRepository.findGenreById(genreId);
            if (genre == null) throw new ApiException("Genre not found");

            game.setGenre(genre);
            genre.getGames().add(game);

            genreRepository.save(genre);
            gameRepository.save(game);
        }


        public Game convertGameIDToToGame (GameIDTO gameIDTO){
            return new Game(null, gameIDTO.getName(), gameIDTO.getPrice(), null, gameIDTO.getReleaseDate(), false, gameIDTO.getSize(),
                    null,null,null ,null,null,null, null, null, null, null, null, null, null, null, null);
        }

        public List<GameODTO> convertGameToGameODTO (Collection< Game > games) {
            List<GameODTO> gameODTOS = new ArrayList<>();
            for (Game game : games) {
                gameODTOS.add(new GameODTO(game.getName(), game.getPrice(), game.getReleaseDate(), game.getSize()));
            }
            return gameODTOS;
        }



}
