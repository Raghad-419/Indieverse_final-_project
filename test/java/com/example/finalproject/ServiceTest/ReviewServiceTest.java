package com.example.finalproject.ServiceTest;

import com.example.finalproject.DTO.ReviewODTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Review;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.ReviewRepository;
import com.example.finalproject.Service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository = Mockito.mock(ReviewRepository.class);

    @Mock
    AuthRepository authRepository = Mockito.mock(AuthRepository.class);

    @InjectMocks
    ReviewService reviewService = Mockito.mock(ReviewService.class);

    @Test
    void getAllReviews() {
        Review review1 = new Review(null,"review1",4,null,null, LocalDateTime.now(),null,null,null,null,null);
        Review review2 = new Review(null,"review2",3,null,null, LocalDateTime.now(),null,null,null,null,null);

        when(reviewRepository.findAll()).thenReturn(List.of(review1,review2));
        List<ReviewODTO> reviews = reviewService.getAllReviews();

        assertNotNull(reviews);
        Assertions.assertEquals(2,reviews.size());
    }

    @Test
    void getMyReviews(){
        MyUser mockUser = new MyUser(1, "user1", "password", "User One", "user1@example.com", "0557640001", "DEVELOPER", false, null, null, null);
        Review review1 = new Review(null,"review1",4,null,null, LocalDateTime.now(),null,null,null,null,null);
        Review review2 = new Review(null,"review2",3,null,null, LocalDateTime.now(),null,null,null,null,null);

        when(authRepository.findMyUserById(mockUser.getId())).thenReturn(mockUser);
        when(reviewRepository.findReviewsByPlayer_Id(mockUser.getId())).thenReturn(List.of(review1,review2));
        List<ReviewODTO> reviews = reviewService.getMyReviews(mockUser.getId());
        assertNotNull(reviews);
        Assertions.assertEquals(2,reviews.size());

    }
}
