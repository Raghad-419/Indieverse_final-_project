package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.PlayerIDTO;
import com.example.finalproject.DTO.PlayerODTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Service.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/get-player")
    public ResponseEntity<PlayerODTO> getMyPlayer(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(playerService.getMyPlayer(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid PlayerIDTO playerIDTO){
        playerService.register(playerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updatePlayer(@AuthenticationPrincipal MyUser user, @RequestBody @Valid PlayerIDTO playerIDTO){
        playerService.updatePlayer(user.getId(), playerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deletePlayer(@AuthenticationPrincipal MyUser user){
         playerService.deletePlayer(user.getId());
         return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted"));
    }

    @GetMapping("/get-all-players")
    public ResponseEntity getAllPlayers(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(playerService.getAllPlayers(user.getId()));
    }
}
