package com.sabrina.studynow.course.mode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ModeRepository extends JpaRepository<Mode, Long> {

}
