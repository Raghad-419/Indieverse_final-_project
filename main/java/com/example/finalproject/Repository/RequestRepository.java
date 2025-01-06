package com.example.finalproject.Repository;

import com.example.finalproject.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {
    Request findRequestsByRequestId(Integer requestId);
    Request findRequestBySenderId(Integer senderId);
    List<Request> findRequestsByReceiverId(Integer receiverId);
    Request findRequestsByReceiverIdAndRequestId(Integer receiverId, Integer requestId);


}
