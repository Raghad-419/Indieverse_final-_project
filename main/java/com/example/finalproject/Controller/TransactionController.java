package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/buy-game/{gameId}")
    public ResponseEntity buyGame(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        return ResponseEntity.status(200).body(transactionService.buyGame(user.getId(), gameId));
    }

    @GetMapping("/get-player-history")
    public ResponseEntity getPlayerTransactionHistory(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(transactionService.getPlayerTransactionHistory(user.getId()));
    }

    @PostMapping("/pre-order/{gameId}")
    public ResponseEntity preOrderGame(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        transactionService.preOrderGame(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("pre-order completed"));
    }

    @PostMapping("/review-trial/{gameId}")
    public ResponseEntity reviewTrial(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        return ResponseEntity.status(200).body(transactionService.reviewTrial(user.getId(), gameId));
    }
}
