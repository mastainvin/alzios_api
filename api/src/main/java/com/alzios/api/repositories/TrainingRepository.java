package com.alzios.api.repositories;

import com.alzios.api.domain.Training;
import com.alzios.api.domain.TrainingType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    Training findByName(String name);

    Optional<Training> findFirstByTrainingTypeAndDuration(TrainingType trainingType, Integer duration);


    /**
     * Query returns the list of training that the user is associated.
     * @param userId user concerned
     * @return a list of training for the user
     */
    @Query(value = " SELECT t.*  FROM training t, program p, availability a, user u, program_availabilities pa, user_availabilities ua WHERE t.program_id = p.id AND pa.program_id = p.id AND pa.availabilities_id = a.id AND ua.user_id = u.id AND ua.availability_id = a.id AND p.goal_id = u.goal_id AND a.layout = t.layout AND u.id = :userId" +
            " AND (SELECT count(ua2.user_id) FROM user_availabilities ua2 WHERE ua2.user_id = ua.user_id) = (SELECT count(pa2.program_id) FROM program_availabilities pa2 WHERE pa2.program_id = pa.program_id);",nativeQuery = true)
    List<Training> findTrainingsByUserId(String userId);
}