package com.example.finalproject.Repository;

import com.example.finalproject.Model.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Integer> {
    Engine findEngineById(Integer id);
}
