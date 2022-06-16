package com.alzios.api.repositories;

import com.alzios.api.domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}