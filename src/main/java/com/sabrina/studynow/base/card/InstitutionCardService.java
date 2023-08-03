package com.sabrina.studynow.base.card;

import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.institution.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstitutionCardService implements CardService {

    private final InstitutionService institutionService;
    private final RateService rateService;

    @Autowired
    public InstitutionCardService(InstitutionService institutionService, RateService rateService) {
        this.institutionService = institutionService;
        this.rateService = rateService;
    }

    @Override
    public List<? extends CardData> getAllByOwnerId(Long ownerId) {
        return institutionService.getAllCards();
    }

    @Override
    public List<? extends CardData> getAll() {
        return institutionService.getAllCards();
    }

    @Override
    public Integer getAverageRateById(Long id) {
        return rateService.getAverageRateByInstitutionId(id);
    }
}
