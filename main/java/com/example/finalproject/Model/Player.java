package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class Player {

    @Id
    private Integer id;

    @Column(columnDefinition = "int")
    private Integer gamesPurchased=0;




    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @ManyToMany(mappedBy = "players")
    private Set<Game> games;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wishlist wishList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Set<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "player")
    private Set<Reaction> reactions;
}
