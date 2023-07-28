package com.sabrina.studynow.institution;

import com.sabrina.studynow.institution.card.InstitutionCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public Institution getById(Long id) {
    	return institutionRepository.findById(id).orElse(null);
    }

    public Institution getByUserId(Long userId) {
    	return Optional.ofNullable(institutionRepository.findByUserId(userId))
                .orElse(new InstitutionNullObject());
    }

    public void save(Institution institution) {
        institutionRepository.save(institution);
    }

    public void delete(Long id) {
    	institutionRepository.deleteById(id);
    }

    public void update(Institution institution) {
    	institutionRepository.save(institution);
    }

    public List<Institution> getAll() {
    	return institutionRepository.findAll();
    }

    public List<InstitutionCard> getAllCards() {
    	return institutionRepository.findAllCards();
    }

}
