package com.alzios.api.repositories;

import com.alzios.api.domain.UserExerciseData;
import com.alzios.api.domain.embeddedIds.UserExerciseDataId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserExerciseDataRepository extends JpaRepository<UserExerciseData, UserExerciseDataId> {

    Optional<UserExerciseData> findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(Long exerciceId, String userId);

    List<UserExerciseData> findByUserExerciseDataIdUserId(String userExerciseDataId_user_id);


}