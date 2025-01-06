package com.example.finalproject.RepositoryTest;

import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Reviewer;
import com.example.finalproject.Repository.DeveloperRepository;
import com.example.finalproject.Repository.ReviewerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.assertj.core.api.Assertions;

import java.util.List;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DeveloperRepositoryTest {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        MyUser user1 = entityManager.persistAndFlush(new MyUser(null, "user1", "12345", "user1", "user1@example.com", "0557640001", "DEVELOPER", false, null, null, null));
        MyUser user2 = entityManager.persistAndFlush(new MyUser(null, "user2", "12345", "user2", "user2@example.com", "0557640002", "DEVELOPER", false, null, null, null));
        MyUser user3 = entityManager.persistAndFlush(new MyUser(null, "user3", "12345", "user3", "user3@example.com", "0557640003", "DEVELOPER", false, null, null, null));

        Developer developer1 = new Developer(null, "Bio of Developer 1", true, user1, null, null, null, null);
        Developer developer2 = new Developer(null, "Bio of Developer 2", false, user2, null, null, null, null);
        Developer developer3 = new Developer(null, "Bio of Developer 3", true, user3, null, null, null, null);

        entityManager.persistAndFlush(developer1);
        entityManager.persistAndFlush(developer2);
        entityManager.persistAndFlush(developer3);
    }

    @Test
    void testFindDeveloperById() {
        Developer developer = developerRepository.findDeveloperById(1); // Ensure this ID exists in your test DB

        Assertions.assertThat(developer).isNotNull();
        Assertions.assertThat(developer.getId()).isEqualTo(1);
        Assertions.assertThat(developer.getBio()).isEqualTo("Bio of Developer 1");
    }

    @Test
    void testFindDevelopersByValidated() {
        List<Developer> validatedDevelopers = developerRepository.findDevelopersByValidated(true);

        Assertions.assertThat(validatedDevelopers).hasSize(2);
        Assertions.assertThat(validatedDevelopers).extracting("bio")
                .containsExactlyInAnyOrder("Bio of Developer 1", "Bio of Developer 3");

        List<Developer> nonValidatedDevelopers = developerRepository.findDevelopersByValidated(false);

        Assertions.assertThat(nonValidatedDevelopers).hasSize(1);
        Assertions.assertThat(nonValidatedDevelopers.get(0).getBio()).isEqualTo("Bio of Developer 2");
    }

}
