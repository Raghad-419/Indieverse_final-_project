package com.example.finalproject.DTO;

import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WishlistODTO {





    private Player player;

    private List<GameODTO> games;

}
