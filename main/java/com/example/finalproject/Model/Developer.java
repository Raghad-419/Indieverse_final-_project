package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Developer {

    @Id
    private Integer id;

    @Column
    private String bio;

    @Column(columnDefinition = "BOOLEAN not null default false")
    private boolean validated=false;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;

    @ManyToMany(mappedBy = "developers")
    private Set<Platform> platforms;

    @ManyToMany(mappedBy = "developers")
    private Set<Engine> engines;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private Set<Request> requests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private Set<Game> games;

}
