package com.alzios.api.repositories;

import com.alzios.api.domain.Serie;
import com.alzios.api.dtos.SerieDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.SqlResultSetMapping;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    @Transactional
    @Modifying
    @Query("delete from Serie s where s.user.id = :userId AND s.date is null AND s.inActualWeek = true")
    void deleteNotDoneByUserId(@Param("userId") String userId);

    @Transactional
    @Modifying
    @Query("update Serie s set s.inActualWeek = false where s.user.id  = :userId")
    void finishAllByUserId(@Param("userId") String userId);

    @Query("select s from Serie s where s.user.id = :userId")
    List<Serie> findByUserId(@Param("userId") String userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.inActualWeek = true")
    List<Serie> findActualWeekByUserId(@Param("userId") String userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.inActualWeek = true AND not s.date is null  ")
    List<Serie> findActualWeekAndDoneByUserId(@Param("userId") String userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.date is null AND s.inActualWeek = true")
    List<Serie> findNextTrainingByUserId(@Param("userId") String userId);

    /**
     * Query used to find best series of an exercise.
     * For each days that you have trained an exercise, this query will find the best
     * serie of that day.
     * @param userId the user concerned
     * @param exerciseId for this exercise
     * @return found series
     */
    @Query(value = "select s.date, s.weight * (1 + 30 / s.repetitions) as weight from serie s " +
            "where s.user_id = :userId " +
            "and s.exercise_id = :exerciseId " +
            "and not s.date is null " +
            "and s.weight * (1 + 30 / s.repetitions) >= (select max(s1.weight * (1 + 30 / s1.repetitions)) from serie s1 where s1.user_id = s.user_id and s1.exercise_id = s.exercise_id and s1.date = s.date) " +
            "group by s.date, weight", nativeQuery = true)
    List<BestSeries> findPreviousBestSeries(@Param("userId") String userId, @Param("exerciseId") Long exerciseId);


    interface BestSeries {
        Date getDate();
        Double getWeight();
    }


    /**
     * Query used to get every date that an user trained.
     * @param userId user concerned
     * @return
     */
    @Query("select s.date from Serie s where s.user.id = :userId and not s.date is null group by s.date order by s.date desc ")
    List<String> findPreviousTrainingDates(@Param("userId") String userId, Pageable pageable);

    @Query("select s from Serie s where s.user.id = :userId and not s.date = :seriesDate")
    List<Serie> findSeriesByDate(@Param("userId") String userId, @Param("seriesDate") String seriesDate);


}