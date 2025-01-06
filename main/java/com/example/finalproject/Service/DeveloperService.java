package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.*;
import com.example.finalproject.Model.*;
import com.example.finalproject.Repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DeveloperService {
    private final AuthRepository authRepository;
    private final GameRepository gameRepository;
    private final DeveloperRepository developerRepository;
    private final ReviewRepository reviewRepository;
    private final RequestRepository requestRepository;
    private final RequestService requestService;

    //Get developer by id
    public DeveloperODTO getMyDeveloper(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");
        if (user.getDeveloper() == null) throw new ApiException("developer not found");

        return convertDeveloperToDeveloperODTO(user.getDeveloper());
    }

    public void register(DeveloperIDTO developerIDTO) {
        Developer developer = convertDeveloperDTOToDeveloper(developerIDTO);
        developerRepository.save(developer);

        Request request = new Request(null,developer.getId(),1,"VALIDATION",LocalDateTime.now(),"PENDING",developer,null,null,null,null);
        requestRepository.save(request);
    }

    public void updateDeveloper(Integer userId, DeveloperIDTO developerIDTO) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        Developer oldDeveloper = developerRepository.findDeveloperById(userId);
        if (oldDeveloper == null) throw new ApiException("developer not found");

        oldDeveloper.getMyUser().setUsername(developerIDTO.getUsername());
        oldDeveloper.getMyUser().setPassword(developerIDTO.getPassword());
        oldDeveloper.getMyUser().setName(developerIDTO.getName());
        oldDeveloper.getMyUser().setEmail(developerIDTO.getEmail());
        oldDeveloper.getMyUser().setPhoneNumber(developerIDTO.getPhoneNumber());
        oldDeveloper.setBio(developerIDTO.getBio());

        developerRepository.save(oldDeveloper);


    }


    public void deleteDeveloper(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("User not found");

        if (user.isBanned()) throw new ApiException("User is banned");

        Developer oldDeveloper = developerRepository.findDeveloperById(userId);
        if (oldDeveloper == null) throw new ApiException("Developer not found");

        if (oldDeveloper.getGames() != null) {
            oldDeveloper.getGames().forEach(game -> game.setDeveloper(null));
            oldDeveloper.setGames(null);
        }

        if (oldDeveloper.getEngines() != null) {
            oldDeveloper.getEngines().forEach(engine -> engine.setDevelopers(null));
            oldDeveloper.setEngines(null);
        }

        if (oldDeveloper.getPlatforms() != null) {
            oldDeveloper.getPlatforms().forEach(platform -> platform.setDevelopers(null));
            oldDeveloper.setPlatforms(null);
        }

        developerRepository.save(oldDeveloper);

        user.setDeveloper(null);
        authRepository.save(user);

        developerRepository.delete(oldDeveloper);
        authRepository.delete(user);
    }




    public List<GameODTO> getAllMyGames(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        Developer developer = developerRepository.findDeveloperById(userId);

        List<GameODTO> gameODTOS = new ArrayList<>();
        for (Game game : developer.getGames()) {
            GameODTO gameODTO = new GameODTO();
            gameODTO.setName(game.getName());
            gameODTO.setPrice(game.getPrice());
            gameODTO.setSize(game.getSize());
            gameODTOS.add(gameODTO);
        }

        return gameODTOS;
    }

    public GameODTO searchMyGame(String gameName) {

        Game game = gameRepository.findGameByName(gameName);
        if (game == null) {
            throw new ApiException("Game not found");
        }

        GameODTO gameODTO = new GameODTO();
        gameODTO.setName(game.getName());
        gameODTO.setPrice(game.getPrice());
        gameODTO.setSize(game.getSize());


        return gameODTO;
    }



    public List<DeveloperODTO> getTopDevelopersByGameCount(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        return convertDeveloperToDeveloperODTO(developerRepository.findTopDevelopersByGameCount());
    }


    public void proposeCollaboration(Integer userId, Integer targetId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        if(user.isBanned())throw new ApiException("User is banned");


        Developer target = developerRepository.findDeveloperById(targetId);
        if (target == null) throw new ApiException("developer not found");

        if(target.getMyUser().isBanned())throw new ApiException("The other user is banned");

        if (userId.equals(targetId)) throw new ApiException("A developer cannot collaborate with themselves.");

        Request collaborationRequest = new Request();
        collaborationRequest.setType("COLLABORATION");
        collaborationRequest.setRequestDate(LocalDateTime.now());
        collaborationRequest.setStatus("PENDING");
        collaborationRequest.setSenderId(userId);
        collaborationRequest.setReceiverId(targetId);

        requestRepository.save(collaborationRequest);
    }

    public List<RequestODTO> getCollaborationRequests(Integer userId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        List<Request> requests = requestRepository.findRequestsByReceiverId(userId);
        if (requests.isEmpty()) throw new ApiException("No requests found");

        return requestService.convertRequestListToDTO(requests);
    }

    public void acceptCollaborationRequest(Integer userId, Integer requestId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        if(user.isBanned())throw new ApiException("User is banned");

        Request collaborationRequest = requestRepository.findRequestsByReceiverIdAndRequestId(userId, requestId);
        if (collaborationRequest == null) throw new ApiException("Collaboration request not found");

        if (!collaborationRequest.getStatus().equalsIgnoreCase("PENDING")) throw new ApiException("Collaboration request is already accepted or rejected");

        collaborationRequest.setStatus("ACCEPTED");
        requestRepository.save(collaborationRequest);
    }

    public void rejectCollaborationRequest(Integer userId, Integer requestId) {
        MyUser user = authRepository.findMyUserById(userId);
        if (user == null) throw new ApiException("user not found");

        if(user.isBanned())throw new ApiException("User is banned");


        Request collaborationRequest = requestRepository.findRequestsByReceiverIdAndRequestId(userId, requestId);
        if (collaborationRequest == null) throw new ApiException("Collaboration request not found");

        if (!collaborationRequest.getStatus().equalsIgnoreCase("PENDING")) throw new ApiException("Collaboration request is already accepted or rejected");

        collaborationRequest.setStatus("REJECTED");
        requestRepository.save(collaborationRequest);
    }



    public Developer convertDeveloperDTOToDeveloper(DeveloperIDTO developerIDTO) {
        Developer developer = new Developer();

        String hashPassword = new BCryptPasswordEncoder().encode(developerIDTO.getPassword());

        MyUser myUser = new MyUser(null, developerIDTO.getUsername(), hashPassword, developerIDTO.getName()
                , developerIDTO.getEmail(), developerIDTO.getPhoneNumber(),"DEVELOPER",false,null,null,null);
        developer.setMyUser(myUser);

        developer.setBio(developerIDTO.getBio());

        authRepository.save(myUser);

        return developer;
    }

    public DeveloperODTO convertDeveloperToDeveloperODTO(Developer developer) {
        return new DeveloperODTO(developer.getMyUser().getUsername(),developer.getMyUser().getName()
                ,developer.getMyUser().getEmail(),developer.getMyUser().getPhoneNumber(), developer.getBio());
    }

    public List<DeveloperODTO> convertDeveloperToDeveloperODTO(List<Developer> developers) {
        List<DeveloperODTO> developerODTOS = new ArrayList<>();
        for (Developer developer : developers) {
            developerODTOS.add(convertDeveloperToDeveloperODTO(developer));
        }
        return developerODTOS;
    }

}
