package com.alzios.api.repositories;

import com.alzios.api.domain.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
    ExerciseType findByName(String name);
}