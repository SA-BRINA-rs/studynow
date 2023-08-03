package com.sabrina.studynow.base.card;

import com.sabrina.studynow.course.rate.RatableService;

import java.util.List;

public interface CardService extends RatableService {

    List<? extends CardData> getAllByOwnerId(Long ownerId);
    List<? extends CardData> getAll();
}
