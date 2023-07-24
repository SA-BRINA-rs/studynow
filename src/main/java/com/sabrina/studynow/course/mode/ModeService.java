package com.sabrina.studynow.course.mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeService {

    private final ModeRepository modeRepository;

    @Autowired
    public ModeService(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    public Mode getById(Long id) {
    	return modeRepository.findById(id).orElse(null);
    }

    public void add(Mode mode) {
        modeRepository.save(mode);
    }

    public void delete(Long id) {
    	modeRepository.deleteById(id);
    }

    public void update(Mode mode) {
    	modeRepository.save(mode);
    }

    public List<Mode> getAll() {
    	return modeRepository.findAll();
    }

}
