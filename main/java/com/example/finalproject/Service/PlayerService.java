package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.PlayerIDTO;
import com.example.finalproject.DTO.PlayerODTO;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.GameRepository;
import com.example.finalproject.Repository.PlayerRepository;
import com.example.finalproject.Repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AuthRepository authRepository;
    private final RequestRepository requestRepository;

    @Transactional
    public void register(PlayerIDTO playerIDTO) {
        Player player = convertPlayerIDTOToPlayer(playerIDTO);
        playerRepository.save(player);

        Request request = new Request(null,player.getId(),1,"VALIDATION", LocalDateTime.now(),"PENDING",null,player,null,null,null);
        requestRepository.save(request);
    }

    public PlayerODTO getMyPlayer(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("user not found");
        if (user.getPlayer() == null) throw new ApiException("player not found");

        return convertPlayerToPlayerOutDto(user.getPlayer());
    }

    public void updatePlayer(Integer userId, PlayerIDTO playerIDTO) {
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("user not found");

        Player oldPlayer = playerRepository.findPlayerById(userId);
        if(oldPlayer == null) throw new ApiException("player not found");

        user.setUsername(playerIDTO.getUsername());
        user.setPassword(playerIDTO.getPassword());
        user.setName(playerIDTO.getName());
        user.setEmail(playerIDTO.getEmail());
        user.setPhoneNumber(playerIDTO.getPhoneNumber());

        oldPlayer.setMyUser(user);
        playerRepository.save(oldPlayer);
    }

    public void deletePlayer(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        if (user.isBanned()) throw new ApiException("User is banned");

        Player oldPlayer = playerRepository.findPlayerById(userId);
        if (oldPlayer == null) throw new ApiException("Player not found");

        if (oldPlayer.getGames() != null) {
            oldPlayer.getGames().forEach(game -> game.getPlayers().remove(oldPlayer));
            oldPlayer.setGames(null);
        }

        if (oldPlayer.getWishList() != null) {
            oldPlayer.getWishList().setPlayer(null);
            oldPlayer.setWishList(null);
        }

        if (oldPlayer.getRequests() != null) {
            oldPlayer.getRequests().forEach(request -> request.setPlayer(null));
            oldPlayer.setRequests(null);
        }

        if (oldPlayer.getReviews() != null) {
            oldPlayer.getReviews().forEach(review -> review.setPlayer(null));
            oldPlayer.setReviews(null);
        }

        if (oldPlayer.getTransactions() != null) {
            oldPlayer.getTransactions().forEach(transaction -> transaction.setPlayer(null));
            oldPlayer.setTransactions(null);
        }

        if (oldPlayer.getReactions() != null) {
            oldPlayer.getReactions().forEach(reaction -> reaction.setPlayer(null));
            oldPlayer.setReactions(null);
        }

        playerRepository.save(oldPlayer);

        user.setPlayer(null);
        authRepository.save(user);

        playerRepository.delete(oldPlayer);
        authRepository.delete(user);
    }




    public List<PlayerODTO> getAllPlayers(Integer userId){
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("user not found");
        return convertPlayerToPlayerOutDto(playerRepository.findAll());
    }

    public Player convertPlayerIDTOToPlayer(PlayerIDTO playerIDTO) {
        Player player = new Player();

        String hashPassword = new BCryptPasswordEncoder().encode(playerIDTO.getPassword());

        MyUser myUser = new MyUser(null, playerIDTO.getUsername(), hashPassword, playerIDTO.getName()
                ,playerIDTO.getEmail(), playerIDTO.getPhoneNumber(), "PLAYER",false,null,null,null);
        player.setMyUser(myUser);

        authRepository.save(myUser);

        return player;
    }

    public PlayerODTO convertPlayerToPlayerOutDto(Player player) {
        return new PlayerODTO(player.getMyUser().getUsername(),player.getMyUser().getName(),player.getMyUser().getEmail(),player.getMyUser().getPhoneNumber(), player.getGamesPurchased());
    }

    public List<PlayerODTO> convertPlayerToPlayerOutDto(List<Player> players){
        List<PlayerODTO> playerODTOS = new ArrayList<>();
        for(Player player:players){
            playerODTOS.add(new PlayerODTO(player.getMyUser().getUsername(),player.getMyUser().getName(),
                    player.getMyUser().getEmail(), player.getMyUser().getPhoneNumber(),player.getGamesPurchased()));
        }
        return playerODTOS;
    }

}
