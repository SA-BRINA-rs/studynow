package com.sabrina.studynow.course;

import com.sabrina.studynow.course.card.CourseCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Optional<Course> getById(Long id) {
    	return courseRepository.findById(id);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void delete(Long id) {
    	courseRepository.deleteById(id);
    }

    public void update(Course course) {
    	courseRepository.save(course);
    }

    public List<Course> getAll() {
    	return Optional.of(courseRepository.findAll())
                .orElse(Collections.emptyList());
    }

    public List<Course> getAllByModeId(Long modeId) {
    	return Optional.of(courseRepository.findAllByModeId(modeId))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public void deleteAllByModeId(Long modeId) {
    	courseRepository.deleteAllByModeId(modeId);
    }

    public List<CourseCard> getAllCardsByInstitutionId(long l) {
        return courseRepository.findAllCardsByInstitutionId(l);
    }

    public List<CourseCard> getAllCardsByKeyword(Course course, Double priceRange) {
        return courseRepository.searchByCourseCardsKeyword(course, priceRange);
    }

    public Optional<Double> getMaxPriceByAmongAllInstitutions() {
        return Optional.ofNullable(courseRepository.getMaxPriceByAmongAllInstitutions());
    }

    public Optional<Double> getMinPriceAmongAllInstitutionId() {
        return Optional.ofNullable(courseRepository.getMinPriceAmongAllInstitutionId());
    }

    public List<CourseCard> findAllCourseCardsByUserId(Long userId) {
        return courseRepository.findAllCourseCardsByUserId(userId);
    }

    public List<CourseCard> getAllCards() {
        return courseRepository.findAllCards();
    }

    public List<CourseCard> getAllCourseCardsByKeywordAndUserId(Course course, double maxPrice, Long id) {
        return courseRepository.findAllCourseCardsByKeywordAndUserId(course, maxPrice, id);
    }

    public Optional<List<Course>> getAllCoursesByInstitutionId(Long id) {
        return Optional.ofNullable(courseRepository.findAllByInstitutionId(id));
    }

    public Optional<Boolean> existsByNameAndInstitutionId(String courseName, Long institutionId) {
        return Optional.ofNullable(courseRepository.existsByNameAndInstitutionId(courseName, institutionId));
    }
}
