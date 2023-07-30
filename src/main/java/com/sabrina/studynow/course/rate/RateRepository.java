package com.sabrina.studynow.course.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT AVG(r.rate) FROM Rate r WHERE r.course.id = :courseId")
    Integer getAverageRateByCourseId(Long courseId);

    @Modifying
    @Query("DELETE FROM Rate r WHERE r.course.id = ?1")
    void deleteAllByCourseId(Long courseId);

    @Query("SELECT r FROM Rate r WHERE r.course.id = ?1")
    List<Rate> findAllByCourseId(Long id);

    @Query("SELECT r FROM Rate r WHERE r.user.id = ?1 AND r.course.id = ?2")
    Rate getRateByUserId(Long userId, Long courseId);

    @Query("SELECT AVG(r.rate) FROM Rate r WHERE r.course.institution.id = :id")
    Integer getAverageRateByInstitutionId(Long id);
}
