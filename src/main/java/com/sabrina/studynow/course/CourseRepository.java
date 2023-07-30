package com.sabrina.studynow.course;

import com.sabrina.studynow.course.card.CourseCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE " +
            "(:#{#course.name} IS NOT NULL OR LOWER(c.name) LIKE LOWER(concat('%', :#{#course.name}, '%'))) OR " +
            "(:#{#course.subject} IS NOT NULL OR LOWER(c.subject) LIKE LOWER(concat('%', :#{#course.subject}, '%'))) OR " +
            "(:#{#course.description} IS NOT NULL OR LOWER(c.description) LIKE LOWER(concat('%', :#{#course.description}, '%'))) OR " +
//            "(LOWER(c.tags) LIKE concat('%', :#{#course.tags}, '%')) OR " +
            "(c.price BETWEEN :#{#price} AND :maxPrice) OR " +
            "(:#{#startDate} IS NOT NULL OR c.startDate >= :#{#course.startDate}) AND" +
            "(:#{#endDate} IS NOT NULL OR c.endDate <= :#{#course.endDate})")
    List<Course> searchByKeyword(Course course, Double maxPrice);

    @Query("SELECT c FROM CourseCard c")
    List<CourseCard> findAllCards();

    @Modifying
    @Query("DELETE FROM Course c WHERE c.mode.id = ?1")
    void deleteAllByModeId(Long modeId);

    @Query("SELECT c FROM Course c WHERE c.mode.id = ?1")
    List<Course> findAllByModeId(Long modeId);

    @Query("SELECT c FROM CourseCard c WHERE c.institution.id = ?1")
    List<CourseCard> findAllCardsByInstitutionId(long l);

}
