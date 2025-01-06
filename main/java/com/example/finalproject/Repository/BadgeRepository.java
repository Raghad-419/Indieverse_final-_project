package com.example.finalproject.Repository;

import com.example.finalproject.Model.Badge;
import com.example.finalproject.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {
    Badge findBadgeByBadgeId(Integer badgeId);


}
