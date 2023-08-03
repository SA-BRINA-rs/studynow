package com.sabrina.studynow.institution;

import com.sabrina.studynow.institution.card.InstitutionCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("SELECT i FROM Institution i WHERE i.user.id = ?1")
    Institution findByUserId(Long userId);

    @Query("SELECT i FROM Institution i WHERE " +
            "LOWER(i.name) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(i.description) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(i.tags) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Institution> searchByKeyword(String keyword);

    @Query("SELECT c FROM InstitutionCard c")
    List<InstitutionCard> findAllCards();

    @Query("SELECT c FROM InstitutionCard c WHERE " +
            "(CASE WHEN :#{#institution.name} IS NULL THEN FALSE ELSE LOWER(c.name) LIKE LOWER(concat('%', :#{#institution.name}, '%')) END) OR " +
            "(CASE WHEN :#{#institution.phone} IS NULL THEN FALSE ELSE LOWER(c.phone) LIKE LOWER(concat('%', :#{#institution.phone}, '%')) END) OR " +
            "(CASE WHEN :#{#institution.description} IS NULL THEN FALSE ELSE LOWER(c.description) LIKE LOWER(concat('%', :#{#institution.description}, '%')) END) OR " +
            "(CASE WHEN :#{#institution.tags} IS NULL THEN FALSE ELSE LOWER(c.tags) LIKE LOWER(concat('%', :#{#institution.tags}, '%')) END)")
    List<InstitutionCard> searchByInstitutionCardsKeyword(Institution institution);
}
