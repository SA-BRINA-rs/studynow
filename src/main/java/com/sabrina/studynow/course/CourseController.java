package com.sabrina.studynow.course;

import com.sabrina.studynow.course.favorite.Favorite;
import com.sabrina.studynow.course.favorite.FavoriteNullObject;
import com.sabrina.studynow.course.favorite.FavoriteService;
import com.sabrina.studynow.course.rate.Rate;
import com.sabrina.studynow.course.rate.RateNullObject;
import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserNullObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(path = "view")
public class CourseController {

    private final CourseService courseService;
    private final FavoriteService favoriteService;
    private final RateService rateService;

    @Autowired
    public CourseController(
            CourseService courseService,
            FavoriteService favoriteService,
            RateService rateService) {
        this.courseService = courseService;
        this.favoriteService = favoriteService;
        this.rateService = rateService;
    }

    @GetMapping("{id}")
    String getCourse(
            Model model,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {

        SecurityContextHolder.getContext().getAuthentication();
        User userTemp = Optional.ofNullable(user)
                .orElse(new UserNullObject());

        Course course = courseService.getById(id);
        Integer averageRate = Optional.ofNullable(rateService.getAverageRateByCourseId(id))
                .orElse(1);
        course.setAverageRate(averageRate);

        List<Rate> rates = Optional.of(rateService.getALlRatesByCourseId(id))
                .orElse(Collections.emptyList());
        Rate userRate = Optional.ofNullable(rateService.getRateByUserId(userTemp.getId(), id))
                .orElse(new RateNullObject());
        Favorite favorite = Optional.ofNullable(favoriteService.getFavoriteByUserId(userTemp.getId(), id))
                .orElse(new FavoriteNullObject());

        model.addAttribute("course", course);
        model.addAttribute("rates", rates);
        model.addAttribute("userRate", userRate);
        model.addAttribute("favorite", favorite);
        model.addAttribute("user", userTemp);
        model.addAttribute("pageName", "course");

        return "view";
    }

    @PostMapping("/favorites")
    String addFavorite(
            Model model,
            @RequestBody Long id,
            @AuthenticationPrincipal User user) {

        SecurityContextHolder.getContext().getAuthentication();

        Course course = courseService.getById(id);
        Favorite favorite = Optional.ofNullable(favoriteService.getFavoriteByUserId(user.getId(), id))
                .orElse(Favorite.builder()
                        .user(user)
                        .course(course)
                        .build());
        favoriteService.save(favorite);

        return "redirect:/view/" + id;
    }

    @DeleteMapping("/favorites/{id}")
    String deleteFavorite(
            Model model,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {

        SecurityContextHolder.getContext().getAuthentication();
        Favorite favorite = favoriteService.getFavoriteByUserId(user.getId(), id);
        if (Objects.nonNull(favorite)){
            favoriteService.delete(favorite.getId());
        }

        return "redirect:/view/" + id;
    }

    @PostMapping("/rate")
    ResponseEntity<?> addRate(
            Model model,
            @ModelAttribute Rate rate,
            @AuthenticationPrincipal User user) {

        SecurityContextHolder.getContext().getAuthentication();

        Course course = courseService.getById(rate.getCourse().getId());
        rate.setUser(user);
        rate.setCourse(course);
        rateService.save(rate);

        String redirectUrl = "/view/" + rate.getCourse().getId();
        Map<String, String> response = new HashMap<>();
        response.put("url", redirectUrl);
        response.put("message", "Rate added successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/rate/{id}")
    ResponseEntity<?> deleteRate(
            Model model,
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {

        SecurityContextHolder.getContext().getAuthentication();
        Rate rate = rateService.getById(id);
        if (Objects.nonNull(rate)){
            rateService.delete(rate.getId());
        }

        String redirectUrl = "/view/" + rate.getCourse().getId();
        Map<String, String> response = new HashMap<>();
        response.put("url", redirectUrl);
        response.put("message", "Rate removed successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
