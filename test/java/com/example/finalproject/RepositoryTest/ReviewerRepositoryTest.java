package com.example.finalproject.RepositoryTest;

import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Reviewer;
import com.example.finalproject.Repository.ReviewerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewerRepositoryTest {

    @Autowired
    private ReviewerRepository reviewerRepository;

    @BeforeEach
    void setUp() {
        MyUser user1 = new MyUser(null,"user1","12345","user1","user1@example.com","0557640001","REVIEWER",false,null,null,null);
        MyUser user2 = new MyUser(null,"user2","12345","user2","user2@example.com","0557640002","REVIEWER",false,null,null,null);

        Reviewer reviewer1 = new Reviewer(null,"null","Bio of Reviewer1",true,user1,null,null,null,null,null,null);
        Reviewer reviewer2 = new Reviewer(null,"null","Bio of Reviewer2",true,user2,null,null,null,null,null,null);

        reviewerRepository.save(reviewer1);
        reviewerRepository.save(reviewer2);
    }

    @Test
    void testFindReviewerById() {
        Reviewer reviewer = reviewerRepository.findReviewerByReviewerId(1);

        Assertions.assertThat(reviewer).isNotNull();
        Assertions.assertThat(reviewer.getReviewerId()).isEqualTo(1);
        Assertions.assertThat(reviewer.getBio()).isEqualTo("Bio of Reviewer1");
    }

    @Test
    void testFindReviewersByValidated(){
        List<Reviewer> validatedReviewer = reviewerRepository.findReviewersByValidated(true);

        Assertions.assertThat(validatedReviewer).hasSize(2);
    }
}
