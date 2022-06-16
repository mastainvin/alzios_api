package com.alzios.api.repositories;

import com.alzios.api.domain.BiomecanicFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BiomecanicFunctionRepository extends JpaRepository<BiomecanicFunction, Long> {
    BiomecanicFunction findByName(String name);
}