package com.alzios.api.repositories;

import com.alzios.api.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training findByName(String name);

    /**
     * Query returns the list of training that the user is associated.
     * @param userId user concerned
     * @return a list of training for the user
     */
    @Query(value = """
            SELECT t.* FROM training t, program p, availability a, user u, program_availabilities pa, user_availabilities ua 
            WHERE t.program_id = p.id 
            AND pa.program_id = p.id 
            AND pa.availabilities_id = a.id 
            AND ua.user_id = u.id 
            AND ua.availability_id = a.id 
            AND p.goal_id = u.goal_id 
            AND a.layout = t.layout 
            AND u.id = ?1""",nativeQuery = true)
    List<Training> findTrainingsByUserId(Long userId);
}