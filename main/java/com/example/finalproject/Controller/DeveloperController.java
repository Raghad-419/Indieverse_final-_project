package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.DeveloperIDTO;
import com.example.finalproject.DTO.DeveloperODTO;
import com.example.finalproject.DTO.GameODTO;
import com.example.finalproject.DTO.RequestODTO;
import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Service.DeveloperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developer")
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping("/get-developer")
    public ResponseEntity<DeveloperODTO> getMyDeveloper(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(developerService.getMyDeveloper(user.getId()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid DeveloperIDTO developerIDTO) {
        developerService.register(developerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully registered"));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateDeveloper(@AuthenticationPrincipal MyUser user, @RequestBody @Valid DeveloperIDTO developerIDTO) {
        developerService.updateDeveloper(user.getId(),developerIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Successfully updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteDeveloper(@AuthenticationPrincipal MyUser user) {
         developerService.deleteDeveloper(user.getId());
         return ResponseEntity.status(200).body(new ApiResponse("Successfully deleted"));
    }


    @GetMapping("/get-all-my-games")
    public ResponseEntity<List<GameODTO>> getAllMyGames(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(developerService.getAllMyGames(user.getId()));
    }

    @GetMapping("/search-my-game/{gameName}")
    public ResponseEntity<GameODTO> searchMyGame(@PathVariable String gameName) {
        return ResponseEntity.status(200).body(developerService.searchMyGame(gameName));
    }

    @GetMapping("/get-top-developer")
    public ResponseEntity<List<DeveloperODTO>> getTopDevelopersByGameCount(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(developerService.getTopDevelopersByGameCount(user.getId()));
    }

    @PostMapping("/propose-collaboration/{targetId}")
    public ResponseEntity<ApiResponse> proposeCollaboration(@AuthenticationPrincipal MyUser user, @PathVariable Integer targetId) {
        developerService.proposeCollaboration(user.getId(), targetId);
        return ResponseEntity.status(200).body(new ApiResponse("Collaboration proposed"));
    }

    @GetMapping("/get-collaboration-requests")
    public ResponseEntity<List<RequestODTO>> getCollaborationRequests(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(developerService.getCollaborationRequests(user.getId()));
    }

    @PutMapping("/accept-collaboration/{requestId}")
    public ResponseEntity<ApiResponse> acceptCollaborationRequest(@AuthenticationPrincipal MyUser user, @PathVariable Integer requestId) {
        developerService.acceptCollaborationRequest(user.getId(), requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Collaboration accepted"));
    }

    @PutMapping("/reject-collaboration/{requestId}")
    public ResponseEntity<ApiResponse> rejectCollaborationRequest(@AuthenticationPrincipal MyUser user, @PathVariable Integer requestId) {
        developerService.rejectCollaborationRequest(user.getId(), requestId);
        return ResponseEntity.status(200).body(new ApiResponse("Collaboration rejected"));
    }
}
