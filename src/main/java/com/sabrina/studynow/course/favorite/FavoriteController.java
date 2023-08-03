package com.sabrina.studynow.course.favorite;

import com.sabrina.studynow.base.Range;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.card.CourseCard;
import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final CourseService courseService;
    private final RateService rateService;

    @Autowired
    public FavoriteController(
            FavoriteService favoriteService,
            CourseService courseService,
            RateService rateService) {
        this.favoriteService = favoriteService;
        this.courseService = courseService;
        this.rateService = rateService;
    }

    @GetMapping(path = "/{id}")
    String redirectFavorite(@PathVariable("id") Long id) {
        return "redirect:/favorite";
    }

    @GetMapping
    String getFavorite(
            Model model,
            HttpSession session,
            @AuthenticationPrincipal User user) {

        List<CourseCard> cards = Optional.of(courseService.findAllCourseCardsByUserId(user.getId()))
                .orElse(Collections.emptyList());

        cards.forEach(card -> {
            Integer averageRate = Optional.ofNullable(rateService.getAverageRateByCourseId(card.getId()))
                    .orElse(1);
            card.setAverageRate(averageRate);
        });

        getRange(model);
        session.setAttribute("previousPage", "favorite");
        model.addAttribute("referenceId", -1);
        model.addAttribute("cards", cards);
        model.addAttribute("user", user);
        model.addAttribute("pageName", "Favourite");
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
}
