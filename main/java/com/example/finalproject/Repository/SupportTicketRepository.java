package com.example.finalproject.Repository;

import com.example.finalproject.Model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Integer> {
    SupportTicket findSupportTicketById(Integer id);

    List<SupportTicket> findSupportTicketsByStatus(String status);
}
