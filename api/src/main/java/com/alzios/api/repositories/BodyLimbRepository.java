package com.alzios.api.repositories;

import com.alzios.api.domain.BodyLimb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyLimbRepository extends JpaRepository<BodyLimb, Long> {
    BodyLimb findByName(String name);
}