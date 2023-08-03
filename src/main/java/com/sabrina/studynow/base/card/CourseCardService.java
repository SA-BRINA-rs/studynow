package com.sabrina.studynow.base.card;

import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.rate.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCardService implements CardService {

    private final CourseService courseService;
    private final RateService rateService;

    @Autowired
    public CourseCardService(CourseService courseService, RateService rateService) {
        this.courseService = courseService;
        this.rateService = rateService;
    }

    @Override
    public List<? extends CardData> getAllByOwnerId(Long ownerId) {
        return courseService.getAllCardsByInstitutionId(ownerId);
    }

    @Override
    public List<? extends CardData> getAll() {
        return courseService.getAllCards();
    }

    @Override
    public Integer getAverageRateById(Long id) {
        return rateService.getAverageRateByCourseId(id);
    }
}
