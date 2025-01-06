package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.EngineODTO;
import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.Engine;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.DeveloperRepository;
import com.example.finalproject.Repository.EngineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;
    private final DeveloperRepository developerRepository;
    private final AuthRepository authRepository;


    public List<EngineODTO> getAllEngine() {
        return convertEngineToODTO(engineRepository.findAll());
    }


    public List<EngineODTO> convertEngineToODTO(List<Engine> engines) {
        List<EngineODTO> engineODTOS = new ArrayList<>();
        for (Engine engine:engines) {
            engineODTOS.add(new EngineODTO(engine.getName()));
        }
        return engineODTOS;
    }

    public void assignEngineToDeveloper (Integer userId, Integer developerId, Integer engineId){
        MyUser myUser = authRepository.findMyUserById(userId);
        if (myUser == null) throw new ApiException("User not found");

        if(myUser.isBanned())throw new ApiException("User is banned");

        Developer developer = developerRepository.findDeveloperById(developerId);
        if (developer == null) throw new ApiException("Developer not found");

        Engine engine = engineRepository.findEngineById(engineId);
        if (engine == null) throw new ApiException("Engine not found");

        developer.getEngines().add(engine);
        engine.getDevelopers().add(developer);
        engineRepository.save(engine);
        developerRepository.save(developer);

    }
}
