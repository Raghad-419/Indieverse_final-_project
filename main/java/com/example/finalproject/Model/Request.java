package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer requestId;

    private Integer senderId;

    private Integer receiverId;

    @Column(nullable = false)
    private String type;

    private LocalDateTime requestDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JsonIgnore
    private Developer developer;

    @ManyToOne
    @JsonIgnore
    private Player player;

    @ManyToOne
    @JsonIgnore
    private Reviewer reviewer;

    @ManyToOne
    @JsonIgnore
    private Game game;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SupportTicket supportTicket;

}
