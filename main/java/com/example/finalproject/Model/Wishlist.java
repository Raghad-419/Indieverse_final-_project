package com.example.finalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Wishlist {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Player player;

    @ManyToMany(mappedBy = "wishlist")
    private Set<Game> games;
}
