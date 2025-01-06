package com.example.finalproject.Service;

import com.example.finalproject.Api.ApiException;
import com.example.finalproject.DTO.RequestODTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Model.Request;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final AuthRepository authRepository;

    public void addRequest(Request request){
        requestRepository.save(request);
    }


    public RequestODTO convertRequestToDTO(Request request){
        MyUser sender = authRepository.findMyUserById(request.getSenderId());
        if(sender == null)throw new ApiException("Sender not found");

        MyUser receiver = authRepository.findMyUserById(request.getReceiverId());
        if(receiver == null)throw new ApiException("Receiver not found");


        return new RequestODTO(sender.getName(),receiver.getName(),request.getType(),request.getRequestDate(),request.getStatus());
    }

    public List<RequestODTO> convertRequestListToDTO(List<Request> requestList){
        List<RequestODTO> requestODTOList = new ArrayList<>();
        for(Request request : requestList){
            requestODTOList.add(convertRequestToDTO(request));
        }
        return requestODTOList;
    }

}
