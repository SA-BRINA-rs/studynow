package com.sabrina.studynow.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface APITokenRepository extends JpaRepository<APIToken, String> {

    @Query("SELECT t FROM APIToken t WHERE t.user.id = :userId")
    Optional<APIToken> findByUserId(Long userId);
}
