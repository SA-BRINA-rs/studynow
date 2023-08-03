package com.sabrina.studynow.search;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.base.card.CardService;
import com.sabrina.studynow.base.card.CourseCardService;
import com.sabrina.studynow.base.card.InstitutionCardService;
import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.filter.CourseSearch;
import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.institution.InstitutionService;
import com.sabrina.studynow.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceSearchFactory {

    private static CourseCardService courseCardService = null;
    private static InstitutionCardService institutionCardService = null;
    private static CourseService courseService = null;
    private static InstitutionService institutionService = null;

    @Autowired
    public ServiceSearchFactory(
            CourseCardService courseCardService,
            InstitutionCardService institutionCardService,
            CourseService courseService,
            InstitutionService institutionService) {
        ServiceSearchFactory.courseCardService = courseCardService;
        ServiceSearchFactory.institutionCardService = institutionCardService;
        ServiceSearchFactory.courseService = courseService;
        ServiceSearchFactory.institutionService = institutionService;
    }

    public static List<? extends CardData> getAllCardsByKeyWords(CourseSearch courseSearch) {
        Course course = courseSearch.getCourse();
        double maxPrice = courseSearch.getMaxPrice();
        List<? extends CardData> cards = Optional.ofNullable(courseService.getAllCardsByKeyword(course, maxPrice))
                .orElse(Collections.emptyList());
        return getAverageRates(courseCardService, cards);
    }

    public static List<? extends CardData> getAllCardsByKeyWords(Institution institution) {
        List<? extends CardData> cards = Optional.ofNullable(institutionService.getAllCardsByKeyword(institution))
                .orElse(Collections.emptyList());
        return getAverageRates(institutionCardService, cards);
    }

    public static List<? extends CardData>
    getAllCourseCardsByKeywordAndUserId(CourseSearch courseSearch, Long userId) {
        Course course = courseSearch.getCourse();
        double maxPrice = courseSearch.getMaxPrice();
        List<? extends CardData> cards = Optional.ofNullable(courseService
                        .getAllCourseCardsByKeywordAndUserId(course, maxPrice, userId))
                .orElse(Collections.emptyList());
        return getAverageRates(courseCardService, cards);
    }

    public static List<? extends CardData> getAllCourseCardsByUserId(User user) {
        List<? extends CardData> cards = Optional.of(courseService.findAllCourseCardsByUserId(user.getId()))
                .orElse(Collections.emptyList());
        return getAverageRates(courseCardService, cards);
    }

    public static List<? extends CardData> getCardsWithRate(String cardName) {
        return getCardsWithRate(cardName, null);
    }

    public static List<? extends CardData> getCardsWithRate(String cardName, User user) {
        List<? extends CardData> cards = new ArrayList<>();
        if(cardName.equals("course")){
            cards = getAllCardsWithRate(courseCardService);
        } else if(cardName.equals("institution")){
            cards = getAllCardsWithRate(institutionCardService);
        } else if(cardName.equals("favorite")){
            cards = getAllCourseCardsByUserId(user);
        }
        return cards;
    }

    public static List<? extends CardData> getCardsWithRateByOwner(String cardName, Long id) {
        List<? extends CardData> cards = new ArrayList<>();
        if(cardName.equals("course") && id != null && id > 0){
            cards = getCourseCardsWithRateByOwnerId(courseCardService, id);
        } else if(cardName.equals("institution")){
            cards = getCourseCardsWithRateByOwnerId(institutionCardService, id);
        }
        return cards;
    }

    public static List<? extends CardData> getCourseCardsWithRateByOwnerId(CardService cardService, Long id) {
        List<? extends CardData> cards = Optional.ofNullable(cardService.getAllByOwnerId(id))
                .orElse(Collections.emptyList());
        return getAverageRates(cardService, cards);
    }

    public static List<? extends CardData> getAllCardsWithRate(CardService cardService) {
        List<? extends CardData> cards = Optional.ofNullable(cardService.getAll())
                .orElse(Collections.emptyList());
        return getAverageRates(cardService, cards);
    }

    public static List<? extends CardData> getAverageRates(CardService cardService, List<? extends CardData> cards) {
        cards.forEach(card -> {
            Integer averageRate = Optional.ofNullable(cardService.getAverageRateById(card.getCardID()))
                    .orElse(1);
            card.setAvgCardRate(averageRate);
        });
        return cards;
    }

}
