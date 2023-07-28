package com.sabrina.studynow.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Course getById(Long id) {
    	return courseRepository.findById(id).orElse(null);
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

    public Course getCourseWithAverageRateById(Long courseId) {
    	return courseRepository.findCourseWithAverageRateById(courseId);
    }
}
