package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;
    private final SupportTicketRepository supportTicketRepository;
    private final DeveloperRepository developerRepository;
    private final RequestRepository requestRepository;
    private final ReviewerRepository reviewerRepository;
    private final PlayerRepository playerRepository;


    public List<Game> getReleasingGameRequests(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        List<Game> games = gameRepository.findGamesByValidated(false);
        if (games.isEmpty()) throw new ApiException("No games found");
        return games;
    }

    public List<SupportTicket> getSupportTickets(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        List<SupportTicket> supportTickets = supportTicketRepository.findSupportTicketsByStatus("PENDING");
        if (supportTickets.isEmpty()) throw new ApiException("No support tickets found");
        return supportTickets;
    }

    public List<Developer> getUnvalidatedDevelopers(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        List<Developer> developers = developerRepository.findDevelopersByValidated(false);
        if (developers.isEmpty()) throw new ApiException("No developers found");
        return developers;
    }

    public List<Request> getAllRequests(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        List<Request> requests = requestRepository.findRequestsByReceiverId(userId);
        if (requests.isEmpty()) throw new ApiException("No requests found");
        return requests;
    }

    public List<Reviewer> getUnvalidatedReviewers(Integer userId) {
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        List<Reviewer> reviewers = reviewerRepository.findReviewersByValidated(false);
        if (reviewers.isEmpty()) throw new ApiException("No reviewers found");
        return reviewers;
    }

    public void validateGame(Integer userId, Integer gameId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Game game = gameRepository.findGameById(gameId);
        if (game == null) throw new ApiException("Game not found");

        if (game.isValidated()) throw new ApiException("Game is already validated");

        game.setValidated(true);
        gameRepository.save(game);
    }

    public void validateSupportTicket(Integer userId, Integer ticketId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        SupportTicket ticket = supportTicketRepository.findSupportTicketById(ticketId);
        if (ticket == null) throw new ApiException("Ticket not found");

        if (!ticket.getStatus().equalsIgnoreCase("PENDING")) throw new ApiException("Ticket has been validated");

        ticket.setStatus("COMPLETED");
        supportTicketRepository.save(ticket);
    }

    public void validateDeveloper(Integer userId, Integer developerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Developer developer = developerRepository.findDeveloperById(developerId);
        if (developer == null) throw new ApiException("Developer not found");

        if (developer.isValidated()) throw new ApiException("Developer is already validated");

        developer.setValidated(true);
        developerRepository.save(developer);
    }

    public void validateReviewer(Integer userId, Integer reviewerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Reviewer reviewer = reviewerRepository.findReviewerByReviewerId(reviewerId);
        if (reviewer == null) throw new ApiException("Reviewer not found");

        if (reviewer.isValidated()) throw new ApiException("Reviewer is validated");
        

        reviewer.setValidated(true);
        reviewerRepository.save(reviewer);
    }

    public void banPlayer(Integer userId, Integer playerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Player player = playerRepository.findPlayerById(playerId);
        if (player == null) throw new ApiException("Player not found");

        if (player.getMyUser().isBanned()) throw new ApiException("Player is already banned");

        player.getMyUser().setBanned(true);
        authRepository.save(player.getMyUser());
        playerRepository.save(player);
    }

    public void unbanPlayer(Integer userId, Integer playerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");
        Player player = playerRepository.findPlayerById(playerId);
        if (player == null) throw new ApiException("Player not found");

        if (!player.getMyUser().isBanned()) throw new ApiException("Player is already unbanned");

        player.getMyUser().setBanned(false);
        authRepository.save(player.getMyUser());
        playerRepository.save(player);
    }

    public void banDeveloper(Integer userId, Integer developerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Developer developer = developerRepository.findDeveloperById(developerId);
        if (developer == null) throw new ApiException("Developer not found");

        if (developer.getMyUser().isBanned()) throw new ApiException("Developer is already banned");

        developer.getMyUser().setBanned(true);
        authRepository.save(developer.getMyUser());
        developerRepository.save(developer);
    }

    public void unbanDeveloper(Integer userId, Integer developerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        Developer developer = developerRepository.findDeveloperById(developerId);
        if (developer == null) throw new ApiException("Developer not found");

        if (!developer.getMyUser().isBanned()) throw new ApiException("Developer is already unbanned");

        developer.getMyUser().setBanned(false);
        authRepository.save(developer.getMyUser());
        developerRepository.save(developer);
    }

    public void banReviewer(Integer userId, Integer reviewerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");
        Reviewer reviewer = reviewerRepository.findReviewerByReviewerId(reviewerId);
        if (reviewer == null) throw new ApiException("Reviewer not found");

        if (reviewer.getMyUser().isBanned()) throw new ApiException("Reviewer is already banned");

        reviewer.getMyUser().setBanned(true);
        authRepository.save(reviewer.getMyUser());
        reviewerRepository.save(reviewer);
    }

    public void unbanReviewer(Integer userId, Integer reviewerId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");
        Reviewer reviewer = reviewerRepository.findReviewerByReviewerId(reviewerId);
        if (reviewer == null) throw new ApiException("Reviewer not found");

        if (!reviewer.getMyUser().isBanned()) throw new ApiException("Reviewer is already unbanned");

        reviewer.getMyUser().setBanned(false);
        authRepository.save(reviewer.getMyUser());
        reviewerRepository.save(reviewer);
    }

}
