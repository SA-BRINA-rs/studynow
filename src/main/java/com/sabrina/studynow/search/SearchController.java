package com.sabrina.studynow.search;

import com.sabrina.studynow.base.Range;
import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.base.card.CourseCardService;
import com.sabrina.studynow.base.card.InstitutionCardService;
import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.filter.CourseSearch;
import com.sabrina.studynow.course.mode.ModeNullObject;
import com.sabrina.studynow.institution.Institution;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.util.SanitizerUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "search")
public class SearchController {

    private final CourseService courseService;
    private final CourseCardService courseCardService;
    private final InstitutionCardService institutionCardService;

    @Autowired
    public SearchController(
            CourseService courseService,
            CourseCardService courseCardService,
            InstitutionCardService institutionCardService) {
        this.courseService = courseService;
        this.courseCardService = courseCardService;
        this.institutionCardService = institutionCardService;
    }

    @GetMapping
    String searchCards(Model model, @AuthenticationPrincipal User user) {
        return "redirect:/search/course";
    }

    @GetMapping(path = {"/{cardName}", "/{cardName}/{id}"})
    String searchCardsByName(Model model, HttpSession session,
                             @AuthenticationPrincipal User user,
                             @PathVariable("cardName") String cardName,
                             @PathVariable(value = "id", required = false) Long id) {

        List<? extends CardData> cards = fetchCards(cardName, id, session);

        getRange(model);
        ifNotCourseNoFilter(model, cardName);
        model.addAttribute("user", user);
        model.addAttribute("pageName", cardName);
        model.addAttribute("cards", cards);
        return "search";
    }

    @PostMapping(path = {"/", "/{cardName}", "/{cardName}/{id}"})
    String searchByKeywordCourses(Model model, HttpSession session,
              @AuthenticationPrincipal User user,
              @RequestParam(value="search", required = false, defaultValue = "null") String search,
              @RequestParam(value="price_range", required = false, defaultValue = "null") String priceRange,
              @RequestParam(value="startDate", required = false, defaultValue = "null") String startDate,
              @RequestParam(value="endDate", required = false, defaultValue = "null") String endDate,
              @RequestParam(value="applyRange", required = false, defaultValue = "null") String applyRange,
              @PathVariable(value = "cardName", required = false) String cardName,
              @PathVariable(value = "id", required = false) Long id) {

        double priceMin = -1d;
        double priceMax = -1d;
        if (SanitizerUtil.parseBoolean(applyRange)) {
            priceMin = SanitizerUtil.parseBoolean(applyRange) ? SanitizerUtil.parsePrice(priceRange) : -1;
            priceMax = courseService.getMaxPriceByAmongAllInstitutions();
        }

        if(cardName != null && cardName.equals("course")) {

            Long institutionId = (id != null) ? id : -1;
            Institution institution = Institution.builder().id(institutionId).build();

            Course course = Course.builder()
                    .id(-1L)
                    .institution(institution)
                    .mode(new ModeNullObject())
                    .name(SanitizerUtil.parseString(search))
                    .subject(SanitizerUtil.parseString(search))
                    .description(SanitizerUtil.parseString(search))
                    .price(priceMin)
                    .startDate(SanitizerUtil.parseDate(startDate))
                    .endDate(SanitizerUtil.parseDate(endDate))
                    .build();

            CourseSearch courseSearch = CourseSearch.builder()
                    .course(course).maxPrice(priceMax).build();
            session.setAttribute("courseSearch", courseSearch);

        } else if(cardName != null && cardName.equals("institution")) {

            Institution institution = Institution.builder()
                    .id(-1L)
                    .name(SanitizerUtil.parseString(search))
                    .description(SanitizerUtil.parseString(search))
                    .phone(SanitizerUtil.parseString(search))
                    .build();

            session.setAttribute("institutionSearch", institution);

        }

        return "redirect:/search/" + cardName;
    }

    private void getRange(Model model){
        double minPrice = courseService.getMinPriceAmongAllInstitutionId();
        double maxPrice = courseService.getMaxPriceByAmongAllInstitutions();
        double value = Math.round(maxPrice / 2);

        Range range = Range.builder().min(minPrice).max(maxPrice)
                .step(10.00).value(value).build();
        model.addAttribute("range", range);
    }

    private void ifNotCourseNoFilter(Model model, String cardName){
        model.addAttribute("isFilter", cardName.equals("course"));
        model.addAttribute("actionUrl","/search/" + cardName);
    }

    private List<? extends CardData> fetchCards(String cardName, Long id, HttpSession session) {

        List<? extends CardData> cards;
        CourseSearch courseSearch = (CourseSearch) session.getAttribute("courseSearch");
        session.removeAttribute("courseSearch");

        Institution institution = (Institution) session.getAttribute("institutionSearch");
        session.removeAttribute("institutionSearch");

        if (courseSearch != null){
            return ServiceSearchFactory.getAllCardsByKeyWords(courseSearch);
        } else if (institution != null){
            return ServiceSearchFactory.getAllCardsByKeyWords(institution);
        } else if(id != null){
            cards = ServiceSearchFactory.getCardsWithRateByOwner(cardName, id);
        } else {
            cards = ServiceSearchFactory.getCardsWithRate(cardName);
        }
        return cards;
    }
}
