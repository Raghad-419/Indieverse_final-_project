package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload/{gameId}")
    public ResponseEntity<ApiResponse> uploadImage(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId, @RequestParam MultipartFile file) {
                try {
                    imageService.uploadImage(user.getId(), gameId, file);
                    return ResponseEntity.status(200).body(new ApiResponse("Successfully uploaded image"));
                }
                catch (IOException e) {
                    return ResponseEntity.status(400).body(new ApiResponse("Failed to upload image"));
                }
    }

    @GetMapping("/get-image/{imageId}")
    public ResponseEntity<byte[]> getImage(@AuthenticationPrincipal MyUser user, @PathVariable Integer imageId) throws IOException {
        byte[] imageData = imageService.getImage(user.getId(), imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/jpeg");
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @PostMapping("/upload-video/{gameId}")
    public ResponseEntity<String> uploadVideo(@AuthenticationPrincipal MyUser user, @PathVariable Integer gameId, @RequestParam("file") MultipartFile file) {
        try {
            imageService.uploadVideo(user.getId(), gameId, file);
            return ResponseEntity.ok("Video uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to upload video");
        }
    }

    @GetMapping("/get-videos/{videoId}")
    public ResponseEntity<byte[]> viewVideo(@PathVariable Integer videoId) {
        try {
            byte[] videoData = imageService.getVideo(videoId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .body(videoData);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
