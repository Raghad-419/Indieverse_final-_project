package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Empty name")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "DOUBLE not null")
    @NotNull(message = "Empty balance")
    @PositiveOrZero(message = "Price must be positive")
    private Double price;

    @Column
    private Double originalPrice;

    @Column(columnDefinition = "date")
    private LocalDate releaseDate ;

    private boolean validated;

    @NotEmpty(message = "Empty name")
    @Column(nullable = false)
    private String size;


    @ManyToOne
    @JsonIgnore
    private Developer developer;

    @ManyToMany
    @JsonIgnore
    private Set<Player> players;

    @ManyToMany(mappedBy = "games")
    private Set<Platform> platforms;

    @ManyToMany
    @JsonIgnore
    private Set<Reviewer> reviewers;

    @ManyToMany
    @JsonIgnore
    private Set<Player> preOrders;


    @ManyToMany
    @JsonIgnore
    private Set<Wishlist> wishlist;

    @ManyToOne
    @JsonIgnore
    private Genre genre;

    @ManyToMany(mappedBy = "games")
    private Set<Tag> tags;

    @ManyToMany(mappedBy = "games")
    private Set<Badge> badges;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<VideoReview> videoReviews;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Analytics analytics;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private Set<Image> images;
}
