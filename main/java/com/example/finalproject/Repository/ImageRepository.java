package com.example.finalproject.Repository;

import com.example.finalproject.Model.Game;
import com.example.finalproject.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findImageById(Integer id);
}
