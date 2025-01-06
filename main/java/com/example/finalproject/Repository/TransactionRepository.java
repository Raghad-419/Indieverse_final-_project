package com.example.finalproject.Repository;

import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Model.Reviewer;
import com.example.finalproject.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTransactionByTransactionId(Integer transactionId);

    boolean existsByPlayerAndGame(Player player, Game game);

    List<Transaction> findTransactionsByGame_Id(Integer gameId);

    boolean existsByReviewerAndGame(Reviewer reviewer, Game game);

    List<Transaction> findAllByPlayerId(Integer playerId);

}
