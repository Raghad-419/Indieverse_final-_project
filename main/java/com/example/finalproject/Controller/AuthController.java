package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PutMapping("/game-release/{gameId}")
    public ResponseEntity validateGameForRelease(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        authService.validateGame(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Game successfully validated"));
    }

    @PutMapping("/support-ticket/{ticketId}")
    public ResponseEntity validateSupportTicket(@AuthenticationPrincipal MyUser user, @PathVariable Integer ticketId) {
        authService.validateSupportTicket(user.getId(), ticketId);
        return ResponseEntity.status(200).body(new ApiResponse("Ticket successfully validated"));
    }

    @PutMapping("/validate-developer/{developerId}")
    public ResponseEntity validateDeveloper(@AuthenticationPrincipal MyUser user, @PathVariable Integer developerId) {
        authService.validateDeveloper(user.getId(), developerId);
        return ResponseEntity.status(200).body(new ApiResponse("Developer successfully validated"));
    }

    @PutMapping("/validate-reviewer/{reviewerId}")
    public ResponseEntity validateReviewer(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewerId) {
        authService.validateReviewer(user.getId(), reviewerId);
        return ResponseEntity.status(200).body(new ApiResponse("Reviewer successfully validated"));
    }

    @GetMapping("/get-releasing-game-requests")
    public ResponseEntity getReleasingGameRequests(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(authService.getReleasingGameRequests(user.getId()));
    }

    @GetMapping("/get-support-tickets")
    public ResponseEntity getSupportTickets(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(authService.getSupportTickets(user.getId()));
    }

    @GetMapping("/get-unvalidated-developers")
    public ResponseEntity getUnvalidatedDevelopers(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(authService.getUnvalidatedDevelopers(user.getId()));
    }

    @GetMapping("/get-unvalidated-reviewers")
    public ResponseEntity getUnvalidatedReviewers(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(authService.getUnvalidatedReviewers(user.getId()));
    }

    @GetMapping("/get-all-requests")
    public ResponseEntity getAllRequests(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(authService.getAllRequests(user.getId()));
    }

    @PutMapping("/ban-player/{playerId}")
    public ResponseEntity banPlayer(@AuthenticationPrincipal MyUser user, @PathVariable Integer playerId) {
        authService.banPlayer(user.getId(), playerId);
        return ResponseEntity.status(200).body(new ApiResponse("Player successfully banned"));
    }

    @PutMapping("/unban-player/{playerId}")
    public ResponseEntity unbanPlayer(@AuthenticationPrincipal MyUser user, @PathVariable Integer playerId) {
        authService.unbanPlayer(user.getId(), playerId);
        return ResponseEntity.status(200).body(new ApiResponse("Player successfully unbanned"));
    }

    @PutMapping("/ban-developer/{developerId}")
    public ResponseEntity banDeveloper(@AuthenticationPrincipal MyUser user, @PathVariable Integer developerId) {
        authService.banDeveloper(user.getId(), developerId);
        return ResponseEntity.status(200).body(new ApiResponse("Developer successfully banned"));
    }

    @PutMapping("/unban-developer/{developerId}")
    public ResponseEntity unbanDeveloper(@AuthenticationPrincipal MyUser user, @PathVariable Integer developerId) {
        authService.unbanDeveloper(user.getId(), developerId);
        return ResponseEntity.status(200).body(new ApiResponse("Developer successfully banned"));
    }

    @PutMapping("/ban-reviewer/{reviewerId}")
    public ResponseEntity banReviewer(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewerId) {
        authService.banReviewer(user.getId(), reviewerId);
        return ResponseEntity.status(200).body(new ApiResponse("Reviewer successfully banned"));
    }

    @PutMapping("/unban-reviewer/{reviewerId}")
    public ResponseEntity unbanReviewer(@AuthenticationPrincipal MyUser user, @PathVariable Integer reviewerId){
        authService.unbanReviewer(user.getId(), reviewerId);
        return ResponseEntity.status(200).body(new ApiResponse("Reviewer successfully banned"));
    }


}
