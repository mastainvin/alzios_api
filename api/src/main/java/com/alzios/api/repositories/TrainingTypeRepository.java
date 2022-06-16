package com.alzios.api.repositories;

import com.alzios.api.domain.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    TrainingType findByName(String name);
}