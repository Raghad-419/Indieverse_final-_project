package com.example.finalproject.Service;

import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Player;
import com.example.finalproject.Model.Transaction;
import com.example.finalproject.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final PlayerRepository playerRepository;

    public void sendEmail(String recipientEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendActivationEmail(String email, String gameName, String activationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Activation Code for " + gameName);
        message.setText("Thank you for pre-ordering " + gameName + ". Your activation code is: " + activationCode);
        mailSender.send(message);
    }

    public void sendBuyEmail(String email, String gameName, Transaction transaction) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Transaction details for game " + gameName);
        message.setText("\nThank you for buying " + gameName
                +"\n\n\n\nOrder Number: " + transaction.getOrderNumber() + "\n\nAmount: " + transaction.getAmount() + " SAR\n\nStatus: " + transaction.getStatus() + "\n\nTransaction Date: " + transaction.getTransactionDate() + "\n\nActivation Code: " + transaction.getActivationCode()
        + "\n\n\n\nEnjoy in your play time and visit us again");
        mailSender.send(message);
    }

    public void sendTrialEmail(String email, String gameName, Transaction transaction) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Transaction details for game " + gameName);
        message.setText("\nThank you for reviewing " + gameName
                +"\n\n\n\nOrder Number: " + transaction.getOrderNumber() + "\n\nAmount: " + transaction.getAmount() + " SAR\n\nStatus: " + transaction.getStatus() + "\n\nTransaction Date: " + transaction.getTransactionDate() + "\n\nActivation Code: " + transaction.getActivationCode()
                + "\n\n\n\nEnjoy in your play time and visit us again");
        mailSender.send(message);
    }


}
