package com.example.finalproject.Repository;

import com.example.finalproject.Model.Badge;
import com.example.finalproject.Model.Developer;
import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Genre;

import jakarta.validation.constraints.NotEmpty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
    Game findGameById(Integer id);

    Game findGameByName(String name);

    Game findGameByIdAndDeveloperId(Integer id, Integer developerId);
    List<Game> findGamesByDeveloper(Developer developer);


    List<Game> findByGenreIn(Set<Genre> genres);

    @Query("SELECT g FROM Game g JOIN g.badges b WHERE b.badgeId = :badgeId")
    List<Game> findGamesByBadgeId(@Param("badgeId") Integer badgeId);

    List<Game> findGamesByGenre(Genre genre);

    List<Game> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Game> findAllByDeveloper_Id(Integer developerId);

    List<Game> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Game> findByReleaseDateAfter(LocalDate date);

    List<Game> findGamesByValidated(boolean validatedForRelease);

    List<Game> findGamesByReleaseDate(LocalDate releaseDate);
//************************
    List<Game> findAllByReleaseDateAfter(LocalDate date);


}
