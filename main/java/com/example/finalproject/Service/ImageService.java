package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.Image;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.GameRepository;
import com.example.finalproject.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;

    public void uploadImage(Integer userId, Integer gameId,MultipartFile file) throws IOException {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        if(myUser.isBanned())throw new ApiException("User is banned");


        Image image = new Image();

        image.setGame(gameRepository.findGameById(gameId));
        image.setImageData(file.getBytes());

        imageRepository.save(image);
    }

    public byte[] getImage(Integer userId , Integer imageId) throws IOException {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("user not found");

        Optional<Image> imageOptional = imageRepository.findById(imageId);
        return imageOptional.map(Image::getImageData).orElseThrow(() -> new IOException("Image not found"));
    }

    public void uploadVideo(Integer userId, Integer gameId, MultipartFile file) throws IOException {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");
        if (myUser.isBanned()) throw new ApiException("User is banned");

        if (!file.getContentType().startsWith("video/")) {
            throw new ApiException("Invalid video file");
        }

        Image video = new Image();
        video.setGame(gameRepository.findGameById(gameId));
        video.setFileName(file.getOriginalFilename());
        video.setContentType(file.getContentType());
        video.setImageData(file.getBytes());

        imageRepository.save(video);
    }

    public byte[] getVideo(Integer videoId) {
        Image video = imageRepository.findById(videoId)
                .orElseThrow(() -> new ApiException("Video not found"));

        return video.getImageData();
    }
}
