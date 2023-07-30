package com.sabrina.studynow.search;

import com.sabrina.studynow.base.Range;
import com.sabrina.studynow.base.card.RangeNullObject;
import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.card.CourseCard;
import com.sabrina.studynow.course.mode.ModeNullObject;
import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.institution.InstitutionService;
import com.sabrina.studynow.institution.card.InstitutionCard;
import com.sabrina.studynow.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        model.addAttribute("referenceId", -1);
        model.addAttribute("range", new RangeNullObject());
        return "search";
    }

    @GetMapping(path = "institution/{id}")
    String searchCourses(
            Model model,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {

        List<CourseCard> cards = getCourseCardsWithRateByInstitutionId(id);
        getRange(model);

        System.out.println(cards.size() + " cards");
        model.addAttribute("user", user);
        model.addAttribute("pageName", "institution");
        model.addAttribute("cards", cards);
        model.addAttribute("referenceId", id);
        return "search";
    }

    private void getRange(Model model){
        double minPrice = courseService.getMinPriceAmongAllInstitutionId();
        double maxPrice = courseService.getMaxPriceByAmongAllInstitutions();
        double value = Math.round(maxPrice / 2);

        Range range = Range.builder()
                .min(minPrice)
                .max(maxPrice)
                .step(10.00)
                .value(value)
                .build();
        model.addAttribute("range", range);
    }

    @PostMapping(path = "/course")
    String searchByKeywordCourses(
            Model model,
            @RequestParam(value="referenceId", required = false, defaultValue = "-1") String referenceId,
            @RequestParam(value="search", required = false, defaultValue = "null") String search,
            @RequestParam(value="price_range", required = false, defaultValue = "null") String priceRange,
            @RequestParam(value="startDate", required = false, defaultValue = "null") String startDate,
            @RequestParam(value="endDate", required = false, defaultValue = "null") String endDate,
            @RequestParam(value="applyRange", required = false, defaultValue = "null") String applyRange,
            @AuthenticationPrincipal User user,
            HttpSession session) {

        Long institutionId = parseReferenceId(referenceId);
        Institution institution = Institution.builder()
                .id(institutionId)
                .build();

        double priceMin = -1.00;
        double priceMax = -1.00;
        if (parseBoolean(applyRange)) {
            priceMin = parseBoolean(applyRange) ? parsePrice(priceRange) : -1;
            priceMax = courseService.getMaxPriceByAmongAllInstitutions();
        }

        Course course = Course.builder()
                .id(-1L)
                .institution(institution)
                .mode(new ModeNullObject())
                .name(parseString(search))
                .subject(parseString(search))
                .description(parseString(search))
                .price(priceMin)
                .startDate(parseDate(startDate))
                .endDate(parseDate(endDate))
                .build();

        List<CourseCard> cards = courseService.getAllCardsByKeyword(course, priceMax);
        cards.forEach(card -> {
            Integer averageRate = Optional.ofNullable(rateService.getAverageRateByCourseId(card.getId()))
                    .orElse(1);
            card.setAverageRate(averageRate);
        });

        session.setAttribute("cards", cards);

        System.out.println(cards.size() + " cards");
        model.addAttribute("cards", cards);
        return "redirect:/search/course/filter";
    }

    @GetMapping(path = "/course/filter")
    String filterCourses(
            Model model,
            @AuthenticationPrincipal User user,
            HttpSession session) {

        List<CourseCard> cards = (List<CourseCard>) session.getAttribute("cards");
        if (Objects.isNull(cards)) {
            return "redirect:/search";
        }

        getRange(model);
        System.out.println(cards.size() + " cards");
        model.addAttribute("cards", cards);
        model.addAttribute("user", user);
        model.addAttribute("pageName", "course Filter");
        session.removeAttribute("cards");
        return "search";
    }

    private Boolean parseBoolean(String bool) {
        if (bool != null && !bool.equalsIgnoreCase("null")){
            return bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("on");
        }
        return false;
    }

    private String parseString(String string) {
        return string != null && !string.equalsIgnoreCase("null") ? string : null;
    }

    private LocalDate parseDate(String date) {
        return date != null && !date.equalsIgnoreCase("null") ? LocalDate.parse(date) : null;
    }

    private Double parsePrice(String price) {
        return price != null && !price.equalsIgnoreCase("null") ? Double.parseDouble(price) : -1.00;
    }

    private Long parseReferenceId(String referenceId) {
        return referenceId != null && !referenceId.equalsIgnoreCase("-1") ? Integer.parseInt(referenceId) : -1L;
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
            Integer averageRate = Optional.ofNullable(rateService.getAverageRateByCourseId(card.getId()))
                    .orElse(1);
            card.setAverageRate(averageRate);
        });
        return cards;
    }

}
