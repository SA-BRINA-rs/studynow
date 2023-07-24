package com.sabrina.studynow.course.rate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RateRepository extends JpaRepository<Rate, Long> {

    @Query("SELECT AVG(r.rate) FROM Rate r WHERE r.course.id = :courseId")
    Integer getAverageRateByCourseId(Long courseId);

}
