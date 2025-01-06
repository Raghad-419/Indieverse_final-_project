package com.example.finalproject.Controller;

import com.example.finalproject.Api.ApiResponse;
import com.example.finalproject.DTO.SupportTicketIDTO;
import com.example.finalproject.Model.MyUser;
import com.example.finalproject.Service.SupportTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/support")
public class SupportTicketController {
    private final SupportTicketService supportTicketService;

    @GetMapping("/get-all-support")
    public ResponseEntity getAllSupportTicket(@AuthenticationPrincipal MyUser user){
        return ResponseEntity.status(200).body(supportTicketService.getAllSupportTicket(user.getId()));
    }

    @PostMapping("/send-support-ticket")
   public ResponseEntity addSupportTicket(@AuthenticationPrincipal MyUser user,@RequestBody @Valid SupportTicketIDTO supportTicketIDTO){
        supportTicketService.addSupportTicket(user.getId(), supportTicketIDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Support Ticket Send successfully"));
   }


}
