package com.sabrina.studynow.institution;

import com.sabrina.studynow.institution.card.InstitutionCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void add(Institution institution) {
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
