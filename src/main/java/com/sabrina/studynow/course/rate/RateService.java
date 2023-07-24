package com.sabrina.studynow.course.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void add(Rate rate) {
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

}
