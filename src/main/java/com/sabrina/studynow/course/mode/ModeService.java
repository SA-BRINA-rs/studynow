package com.sabrina.studynow.course.mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ModeService {

    private final ModeRepository modeRepository;

    @Autowired
    public ModeService(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    public Optional<Mode> getById(Long id) {
    	return modeRepository.findById(id);
    }

    public void save(Mode mode) {
        modeRepository.save(mode);
    }

    public void delete(Long id) {
    	modeRepository.deleteById(id);
    }

    public Optional<Mode> update(Mode mode) {
    	return Optional.ofNullable(modeRepository.save(mode));
    }

    public List<Mode> getAll() {
    	return Optional.of(modeRepository.findAll())
                .orElse(Collections.emptyList());
    }

    public Optional<List<Mode>> getModesByInstitutionId(Long id) {
        return Optional.ofNullable(modeRepository.findByInstitutionId(id));
    }

    public Optional<Boolean> existsByNameAndInstitutionId(String modeName, long institutionId) {
        return Optional.ofNullable(modeRepository.existsByNameAndInstitutionId(modeName, institutionId));
    }
}
