package com.example.finalproject.Repository;

import com.example.finalproject.Model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Genre findGenreById(Integer id);
}
