package com.alzios.api.repositories;

import com.alzios.api.domain.TrainingComponent;
import com.alzios.api.domain.embeddedIds.TrainingComponentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingComponentRepository extends JpaRepository<TrainingComponent, TrainingComponentId> {
}