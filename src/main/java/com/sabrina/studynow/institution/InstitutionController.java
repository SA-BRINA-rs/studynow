package com.sabrina.studynow.institution;

import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.favorite.FavoriteService;
import com.sabrina.studynow.course.mode.Mode;
import com.sabrina.studynow.course.mode.ModeService;
import com.sabrina.studynow.course.rate.RateService;
import com.sabrina.studynow.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "institution")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final ModeService modeService;
    private final CourseService courseService;
    private final FavoriteService favoriteService;
    private final RateService rateService;

    @Autowired
    public InstitutionController(
            InstitutionService institutionService,
            ModeService modeService,
            CourseService courseService,
            FavoriteService favoriteService,
            RateService rateService) {
        this.institutionService = institutionService;
        this.modeService = modeService;
        this.courseService = courseService;
        this.favoriteService = favoriteService;
        this.rateService = rateService;
    }

    @GetMapping
    String getInstitution(Model model, @AuthenticationPrincipal User user) {

        Institution institution = institutionService.getByUserId(user.getId());
        List<Mode> modes = modeService.getAll();
        List<Course> courses = courseService.getAll();

        model.addAttribute("user", user);
        model.addAttribute("institution", institution);
        model.addAttribute("modes", modes);
        model.addAttribute("courses", courses);
        model.addAttribute("pageName", "institution");

        return "institution";
    }

    @PostMapping
    String postInstitution(
            Model model,
            @AuthenticationPrincipal User user,
            Institution institution){

        institution.setUser(user);
        institutionService.save(institution);
        model.addAttribute("user", user);
        model.addAttribute("institution", institution);
        model.addAttribute("pageName", "institution");
        return "institution";
    }

    @PostMapping("/mode")
    String createMode(
            Mode mode,
            @AuthenticationPrincipal User user){

        mode.setId(null);
        modeService.save(mode);
        return "redirect:/institution";
    }

    @DeleteMapping("/mode/{id}")
    ResponseEntity<String> deleteMode(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user){

        List<Course> courses = Optional.of(courseService.getAllByModeId(id))
                .orElse(Collections.emptyList());

        for(Course course : courses){
            favoriteService.deleteAllByCourseId(course.getId());
            rateService.deleteAllByCourseId(course.getId());
        }

        courseService.deleteAllByModeId(id);
        modeService.delete(id);
        return ResponseEntity.ok("Favorites deleted successfully.");
    }

    @PutMapping("/mode/{id}")
    ResponseEntity<String> putMode(
            @AuthenticationPrincipal User user,
            Mode mode){

        modeService.update(mode);
        return ResponseEntity.ok("Favorites deleted successfully.");
    }

    @PostMapping("/course")
    String postCourse(
            Course course,
            @AuthenticationPrincipal User user){
        course.setId(null);
        courseService.save(course);
        return "redirect:/institution";
    }

    @DeleteMapping("/course/{id}")
    ResponseEntity<String> deleteCourse(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user){

        favoriteService.deleteAllByCourseId(id);
        rateService.deleteAllByCourseId(id);
        courseService.delete(id);
        return ResponseEntity.ok("Favorites deleted successfully.");
    }

    @PutMapping("/course")
    ResponseEntity<String> putCourse(
            @AuthenticationPrincipal User user,
            Course course){

        courseService.update(course);
        return ResponseEntity.ok("Course updated successfully.");
    }

}
