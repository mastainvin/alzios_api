package com.alzios.api.repositories;

import com.alzios.api.domain.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Equipment findByName(String name);
}