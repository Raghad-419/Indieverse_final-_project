package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Analytics {

    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer totalDownloads;

    @Column(nullable = false)
    private Integer activePlayers;

    @Column(nullable = false)
    private Double totalEarnings;

    @Column(nullable = false)
    private Double averageRating;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analytics")
    private Set<Review> reviews;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Game game;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analytics")
    private Set<Transaction> transactions;
}
