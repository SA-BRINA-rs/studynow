package com.sabrina.studynow.course.mode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ModeRepository extends JpaRepository<Mode, Long> {

    @Query("SELECT m FROM Mode m WHERE m.institution.id = ?1")
    List<Mode> findByInstitutionId(Long id);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Mode m WHERE m.name = ?1")
    Boolean existsByName(String name);

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END FROM Mode m WHERE m.name = ?1 AND m.institution.id = ?2")
    Boolean existsByNameAndInstitutionId(String modeName, long institutionId);
}
