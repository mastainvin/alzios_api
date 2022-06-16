package com.alzios.api.repositories;

import com.alzios.api.domain.Morphology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MorphologyRepository extends JpaRepository<Morphology, Long> {
    Morphology findByName(String name);
}