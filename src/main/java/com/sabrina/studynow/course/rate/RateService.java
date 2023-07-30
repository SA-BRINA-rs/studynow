package com.sabrina.studynow.course.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RateService {

    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Rate getById(Long id) {
    	return rateRepository.findById(id).orElse(null);
    }

    public void save(Rate rate) {
        rateRepository.save(rate);
    }

    public void delete(Long id) {
    	rateRepository.deleteById(id);
    }

    public void update(Rate rate) {
    	rateRepository.save(rate);
    }

    public List<Rate> getAll() {
    	return rateRepository.findAll();
    }

    public Integer getAverageRateByCourseId(Long courseId) {
    	return rateRepository.getAverageRateByCourseId(courseId);
    }

    @Transactional
    public void deleteAllByCourseId(Long courseId) {
    	rateRepository.deleteAllByCourseId(courseId);
    }

    public List<Rate> getALlRatesByCourseId(Long id) {
        return rateRepository.findAllByCourseId(id);
    }

    public Rate getRateByUserId(Long userId, Long courseId) {
        return rateRepository.getRateByUserId(userId, courseId);
    }

    public Integer getAverageRateByInstitutionId(Long id) {
        return rateRepository.getAverageRateByInstitutionId(id);
    }
}
