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
public class Reviewer {

    @Id
    private Integer reviewerId;

    @Column(nullable = false)
    private String profileUrl;

    @Column(nullable = false)
    private String bio;

    @Column(columnDefinition = "BOOLEAN not null default false")
    private boolean validated=false;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @ManyToMany(mappedBy = "reviewers")
    private Set<Game> games;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reviewer")
    private Set<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewer")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "reviewer")
    private Set<Reaction> reactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewer")
    private Set<Transaction> transactions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewer")
    private Set<VideoReview> videoReviews;


}
