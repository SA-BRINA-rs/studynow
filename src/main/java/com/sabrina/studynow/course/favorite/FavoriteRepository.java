package com.sabrina.studynow.course.favorite;

import com.sabrina.studynow.course.card.CourseCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.course.id = ?1")
    void deleteAllByCourseId(Long courseId);

    @Query("SELECT f FROM Favorite f WHERE f.user.id = ?1 AND f.course.id = ?2")
    Favorite getFavoriteByUserId(Long userId, Long courseId);

}
