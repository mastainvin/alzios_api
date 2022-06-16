package com.alzios.api.repositories;

import com.alzios.api.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    Goal findByName(String name);
}