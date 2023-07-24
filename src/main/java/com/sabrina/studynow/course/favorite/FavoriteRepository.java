package com.sabrina.studynow.course.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
