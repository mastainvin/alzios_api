package com.alzios.api.repositories;

import com.alzios.api.domain.Serie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    @Transactional
    @Modifying
    @Query("delete from Serie s where s.user.id = :userId AND s.date is null AND s.inActualWeek = true")
    void deleteNotDoneByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("update Serie s set s.inActualWeek = false where s.user.id  = :userId")
    void finishAllByUserId(@Param("userId") Long userId);

    @Query("select s from Serie s where s.user.id = :userId")
    List<Serie> findByUserId(@Param("userId") Long userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.inActualWeek = true")
    List<Serie> findActualWeekByUserId(@Param("userId") Long userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.inActualWeek = true AND not s.date is null  ")
    List<Serie> findActualWeekAndDoneByUserId(@Param("userId") Long userId);

    @Query("select s from Serie s where s.user.id = :userId AND s.date is null AND s.inActualWeek = true")
    List<Serie> findNextTrainingByUserId(@Param("userId") Long userId);

    /**
     * Query used to find best series of an exercise.
     * For each days that you have trained an exercise, this query will find the best
     * serie of that day.
     * @param userId the user concerned
     * @param exerciseId for this exercise
     * @return found series
     */
    @Query("select s from Serie s where s.user.id = :userId and s.exercise.id = :exerciseId and not s.date is null and s.repetitions * s.weight >= (select max(s2.repetitions * s2.weight) from Serie s2 where s2.user.id = s.user.id and s2.exercise.id = s.exercise.id and s2.date = s.date)")
    List<Serie> findPreviousBestSeries(@Param("userId") Long userId, @Param("exerciseId") Long exerciseId);

    /**
     * Query used to get every date that an user trained.
     * @param userId user concerned
     * @return
     */
    @Query("select s.date from Serie s where s.user.id = :userId and not s.date is null group by s.date")
    List<String> findPreviousTrainingDates(@Param("userId") Long userId);

    @Query("select s from Serie s where s.user.id = :userId and not s.date = :seriesDate")
    List<Serie> findSeriesByDate(@Param("userId") Long userId, @Param("seriesDate") String seriesDate);


}