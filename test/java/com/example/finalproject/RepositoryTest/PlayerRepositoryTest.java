package com.example.finalproject.RepositoryTest;

import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Model.Reviewer;
import com.example.finalproject.Repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.assertj.core.api.Assertions;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach
    void setUp() {
        MyUser user1 = new MyUser(null,"user1","12345","user1","user1@example.com","0557640001","PLAYER",false,null,null,null);
        MyUser user2 = new MyUser(null,"user2","12345","user2","user2@example.com","0557640002","PLAYER",false,null,null,null);

        Player player1 = new Player(null,0,user1,null,null,null,null,null,null);
        Player player2 = new Player(null,0,user2,null,null,null,null,null,null);

        entityManager.persistAndFlush(player1);
        entityManager.persistAndFlush(player2);
    }

    @Test
    void testFindReviewerById(){
        Player player = playerRepository.findPlayerById(1);

        Assertions.assertThat(player).isNotNull();
        Assertions.assertThat(player.getId()).isEqualTo(1);
        Assertions.assertThat(player.getGamesPurchased()).isEqualTo(0);
    }
}
