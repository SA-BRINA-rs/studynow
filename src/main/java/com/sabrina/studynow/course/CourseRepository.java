package com.sabrina.studynow.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c, AVG(r.rate) FROM Course c LEFT JOIN Rate r ON c.id = r.course.id WHERE c.id = :courseId")
    Course findCourseWithAverageRateById(Long courseId);

    @Query("SELECT c FROM Course c WHERE " +
            "LOWER(c.name) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(c.tags) IN :keyword")
    List<Course> searchByKeyword(String keyword);

}
