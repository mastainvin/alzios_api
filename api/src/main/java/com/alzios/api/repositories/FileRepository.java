package com.alzios.api.repositories;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("select f from File f where f.exercise.id = :exerciseId and f.type = 'image/png'")
    File findPictureByExerciseId(@Param("exerciseId") Long exerciseId);

    @Query("select f from File f where f.exercise.id = :exerciseId and f.type = 'video/mp4'")
    File findVideoByExerciseId(@Param("exerciseId") Long exerciseId);

    @Transactional
    @Modifying
    @Query("delete from File f where f.exercise.id = :exerciseId and f.type = 'image/png'")
    void deletePictureByExerciseId(@Param("exerciseId")  Long exerciseId);

    @Transactional
    @Modifying
    @Query("delete from File f where f.exercise.id = :exerciseId and f.type = 'video/mp4'")
    void deleteVideoByExerciseId(@Param("exerciseId") Long exerciseId);

    @Transactional
    @Modifying
    @Query("update File f set f.exercise.id=null where f.exercise.id = :exerciseId and f.type = 'image/png'")
    void detachPictureByExerciseId(@Param("exerciseId")  Long exerciseId);

    @Transactional
    @Modifying
    @Query("update File f set f.exercise.id=null where f.exercise.id = :exerciseId and f.type = 'video/mp4'")
    void detachVideoByExerciseId(@Param("exerciseId")  Long exerciseId);


}