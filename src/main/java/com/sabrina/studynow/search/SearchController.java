package com.sabrina.studynow.search;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.card.CourseCard;
import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.institution.InstitutionService;
import com.sabrina.studynow.institution.card.InstitutionCard;
import com.sabrina.studynow.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "search")
public class SearchController {

    private final InstitutionService institutionService;
    private final CourseService courseService;
    private final RateService rateService;

    @Autowired
    public SearchController(
            InstitutionService institutionService,
            CourseService courseService,
            RateService rateService) {
        this.institutionService = institutionService;
        this.courseService = courseService;
        this.rateService = rateService;
    }


    @GetMapping(path = "")
    String searchInstitutions(Model model, @AuthenticationPrincipal User user) {

        List<InstitutionCard> cards = getInstitutionCardsWithRate();
        System.out.println(cards.size() + " cards");
        model.addAttribute("user", user);
        model.addAttribute("pageName", "institution");
        model.addAttribute("cards", cards);
        return "search";
    }

    @GetMapping(path = "institution/{id}")
    String searchCourses(
            Model model,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {

        List<CourseCard> cards = getCourseCardsWithRateByInstitutionId(id);

        System.out.println(cards.size() + " cards");
        model.addAttribute("user", user);
        model.addAttribute("pageName", "institution");
        model.addAttribute("cards", cards);
        return "search";
    }

    private List<InstitutionCard> getInstitutionCardsWithRate() {
        List<InstitutionCard> instCards = Optional.ofNullable(institutionService.getAllCards())
                .orElse(Collections.emptyList());

        instCards.forEach(card -> {
            Integer averageRate = Optional.ofNullable(rateService.getAverageRateByInstitutionId(card.getId()))
                    .orElse(1);
            card.setAverageRate(averageRate);
        });
        return instCards;
    }

    private List<CourseCard> getCourseCardsWithRateByInstitutionId(Long id) {
        List<CourseCard> cards = Optional.ofNullable(courseService.getAllCardsByInstitutionId(id))
                .orElse(Collections.emptyList());

        cards.forEach(card -> {
            setAverageRate((CourseCard) card);
        });
        return cards;
    }

    private void setAverageRate(CourseCard courseCard) {
        Integer averageRate = Optional.ofNullable(rateService.getAverageRateByCourseId(courseCard.getId()))
                .orElse(1);
        courseCard.setAverageRate(averageRate);
    }

}
