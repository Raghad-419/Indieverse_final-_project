package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/add-game-to-wishlist/{gameId}")
    public ResponseEntity addGameToWishlist(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        wishlistService.addGameToWishlist(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Game added to wishlist"));
    }

    @DeleteMapping("/remove-game-from-wishlist/{gameId}")
    public ResponseEntity removeGameFromWishlist(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId) {
        wishlistService.removeGameFromWishlist(user.getId(), gameId);
        return ResponseEntity.status(200).body(new ApiResponse("Game removed from Wishlist"));
    }

    @GetMapping("/get-wishlist")
    public ResponseEntity getWishlist(@AuthenticationPrincipal MyUser user) {
        return ResponseEntity.status(200).body(wishlistService.getWishlistById(user.getId()));
    }
}
