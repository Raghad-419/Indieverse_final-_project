package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.ReviewerIDTO;
import com.example.finalproject.DTO.ReviewerODTO;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.GameRepository;
import com.example.finalproject.Repository.RequestRepository;
import com.example.finalproject.Repository.ReviewerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReviewerService {
    private final ReviewerRepository reviewerRepository;
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;
    private final RequestRepository requestRepository;

    public ReviewerODTO getMyReviewer(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("user not found");
        if (user.getReviewer() == null) throw new ApiException("developer not found");

        return convertReviewerToReviewerODTO(user.getReviewer());
    }

    public void register(ReviewerIDTO reviewerIDTO) {
        Reviewer reviewer = convertReviewerIDTOToReviewer(reviewerIDTO);
        reviewerRepository.save(reviewer);

        Request request = new Request(null,reviewer.getReviewerId(),1,"VALIDATION", LocalDateTime.now(),"PENDING",null,null,reviewer,null,null);
        requestRepository.save(request);

    }

    public void updateReviewer(Integer userId, ReviewerIDTO reviewerIDTO) {
        MyUser user = authRepository.findMyUserById(userId);
        if(user == null) throw new ApiException("user not found");

        Reviewer oldReviewer = reviewerRepository.findReviewerByReviewerId(userId);
        if(oldReviewer == null) throw new ApiException("reviewer not found");

        oldReviewer.getMyUser().setUsername(reviewerIDTO.getUsername());
        oldReviewer.getMyUser().setPassword(reviewerIDTO.getPassword());
        oldReviewer.getMyUser().setName(reviewerIDTO.getName());
        oldReviewer.getMyUser().setEmail(reviewerIDTO.getEmail());
        oldReviewer.getMyUser().setPhoneNumber(reviewerIDTO.getPhoneNumber());
        oldReviewer.setBio(reviewerIDTO.getBio());


        reviewerRepository.save(oldReviewer);


    }

    public void deleteReviewer(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        if (user.isBanned()) throw new ApiException("User is banned");

        Reviewer oldReviewer = reviewerRepository.findReviewerByReviewerId(userId);
        if (oldReviewer == null) throw new ApiException("Reviewer not found");

        if (oldReviewer.getGames() != null) {
            oldReviewer.getGames().forEach(game -> game.getReviewers().remove(oldReviewer));
            oldReviewer.setGames(null);
        }

        if (oldReviewer.getReviews() != null) {
            oldReviewer.getReviews().forEach(review -> review.setReviewer(null));
            oldReviewer.setReviews(null);
        }

        if (oldReviewer.getRequests() != null) {
            oldReviewer.getRequests().forEach(request -> request.setReviewer(null));
            oldReviewer.setRequests(null);
        }

        if (oldReviewer.getReactions() != null) {
            oldReviewer.getReactions().forEach(reaction -> reaction.setReviewer(null));
            oldReviewer.setReactions(null);
        }

        if (oldReviewer.getVideoReviews() != null) {
            oldReviewer.getVideoReviews().forEach(videoReview -> videoReview.setReviewer(null));
            oldReviewer.setVideoReviews(null);
        }

        reviewerRepository.save(oldReviewer);

        user.setReviewer(null);
        authRepository.save(user);

        reviewerRepository.delete(oldReviewer);
        authRepository.delete(user);
    }



    public Reviewer convertReviewerIDTOToReviewer(ReviewerIDTO reviewerIDTO) {
        Reviewer reviewer = new Reviewer();

        String hashPassword = new BCryptPasswordEncoder().encode(reviewerIDTO.getPassword());

        MyUser myUser = new MyUser(null, reviewerIDTO.getUsername(), hashPassword, reviewerIDTO.getName()
                , reviewerIDTO.getEmail(), reviewerIDTO.getPhoneNumber(), "REVIEWER",false,null,null,null);

        reviewer.setMyUser(myUser);
        reviewer.setBio(reviewerIDTO.getBio());
        reviewer.setProfileUrl(reviewerIDTO.getProfileUrl());

        authRepository.save(myUser);
        return reviewer;
    }

    public ReviewerODTO convertReviewerToReviewerODTO(Reviewer reviewer) {
        return new ReviewerODTO(reviewer.getMyUser().getUsername(),reviewer.getMyUser().getName(),reviewer.getMyUser().getEmail(),reviewer.getMyUser().getPhoneNumber(),reviewer.getProfileUrl(),reviewer.getBio());
    }
}
