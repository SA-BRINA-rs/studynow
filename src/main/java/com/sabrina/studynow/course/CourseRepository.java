package com.sabrina.studynow.course;

import com.sabrina.studynow.course.card.CourseCard;
import com.sabrina.studynow.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM CourseCard c WHERE " +
            "(CASE WHEN :#{#course.name} IS NULL THEN FALSE ELSE LOWER(c.name) LIKE LOWER(concat('%', :#{#course.name}, '%')) END) OR " +
            "(CASE WHEN :#{#course.subject} IS NULL THEN FALSE ELSE LOWER(c.subject) LIKE LOWER(concat('%', :#{#course.subject}, '%')) END) OR " +
            "(CASE WHEN :#{#course.description} IS NULL THEN FALSE ELSE LOWER(c.description) LIKE LOWER(concat('%', :#{#course.description}, '%')) END) OR " +
            "(CASE WHEN :#{#course.price} IS NULL OR :maxPrice IS NULL THEN FALSE ELSE c.price BETWEEN :#{#course.price} AND :maxPrice END) OR " +
            "(CASE WHEN :#{#course.startDate} IS NULL THEN FALSE ELSE c.startDate >= :#{#course.startDate} END) AND " +
            "(CASE WHEN :#{#course.endDate} IS NULL THEN FALSE ELSE c.endDate <= :#{#course.endDate} END)")
    List<CourseCard> searchByCourseCardsKeyword(Course course, Double maxPrice);

    @Query("SELECT c FROM CourseCard c " +
            "JOIN Favorite f ON c.id = f.course.id " +
            "WHERE (f.user.id = :userId) AND " +
            "(CASE WHEN :#{#course.name} IS NULL THEN FALSE ELSE LOWER(c.name) LIKE LOWER(concat('%', :#{#course.name}, '%')) END) OR " +
            "(CASE WHEN :#{#course.subject} IS NULL THEN FALSE ELSE LOWER(c.subject) LIKE LOWER(concat('%', :#{#course.subject}, '%')) END) OR " +
            "(CASE WHEN :#{#course.description} IS NULL THEN FALSE ELSE LOWER(c.description) LIKE LOWER(concat('%', :#{#course.description}, '%')) END) OR " +
            "(CASE WHEN :#{#course.price} IS NULL OR :maxPrice IS NULL THEN FALSE ELSE c.price BETWEEN :#{#course.price} AND :maxPrice END) OR " +
            "(CASE WHEN :#{#course.startDate} IS NULL THEN FALSE ELSE c.startDate >= :#{#course.startDate} END) AND " +
            "(CASE WHEN :#{#course.endDate} IS NULL THEN FALSE ELSE c.endDate <= :#{#course.endDate} END)")
    List<CourseCard> findAllCourseCardsByKeywordAndUserId(Course course, double maxPrice, Long userId);

    @Query("SELECT c FROM CourseCard c")
    List<CourseCard> findAllCards();

    @Modifying
    @Query("DELETE FROM Course c WHERE c.mode.id = ?1")
    void deleteAllByModeId(Long modeId);

    @Query("SELECT c FROM Course c WHERE c.mode.id = ?1")
    List<Course> findAllByModeId(Long modeId);

    @Query("SELECT c FROM CourseCard c WHERE c.institution.id = ?1")
    List<CourseCard> findAllCardsByInstitutionId(long l);

    @Query("SELECT MAX(c.price) FROM Course c")
    Double getMaxPriceByAmongAllInstitutions();

    @Query("SELECT MIN(c.price) FROM Course c")
    Double getMinPriceAmongAllInstitutionId();

    @Query("SELECT cc FROM CourseCard cc " +
            "JOIN Favorite f ON cc.id = f.course.id " +
            "WHERE f.user.id = :userId")
    List<CourseCard> findAllCourseCardsByUserId(Long userId);

    @Query("SELECT c FROM Course c WHERE c.institution.id = ?1")
    List<Course> findAllByInstitutionId(Long id);

    @Query("SELECT c FROM Course c WHERE c.name = ?1 AND c.institution.id = ?2")
    Boolean existsByNameAndInstitutionId(String courseName, Long institutionId);
}
