package com.alzios.api.repositories;

import com.alzios.api.domain.TrainingComponent;
import com.alzios.api.domain.embeddedIds.TrainingComponentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TrainingComponentRepository extends JpaRepository<TrainingComponent, TrainingComponentId> {

    @Transactional
    @Modifying
    @Query("delete from TrainingComponent t where t.inTraining = false")
    void deleteByInTrainingIsFalse();


}