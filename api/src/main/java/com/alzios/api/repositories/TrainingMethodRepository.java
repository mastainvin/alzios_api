package com.alzios.api.repositories;

import com.alzios.api.domain.TrainingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingMethodRepository extends JpaRepository<TrainingMethod, Long> {
    TrainingMethod findByName(String name);
}