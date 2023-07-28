package com.sabrina.studynow.institution;

import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseService;
import com.sabrina.studynow.course.mode.Mode;
import com.sabrina.studynow.course.mode.ModeService;
import com.sabrina.studynow.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "institution")
public class InstitutionController {

    private final InstitutionService institutionService;
    private final ModeService modeService;
    private final CourseService courseService;

    @Autowired
    public InstitutionController(
            InstitutionService institutionService,
            ModeService modeService,
            CourseService courseService) {
        this.institutionService = institutionService;
        this.modeService = modeService;
        this.courseService = courseService;
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
    String postMode(
            Mode mode,
            @AuthenticationPrincipal User user){

        modeService.save(mode);
        return "redirect:/institution";
    }

    @DeleteMapping("/mode/{id}")
    String deleteMode(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user){
        modeService.delete(id);
        return "redirect:/institution";
    }

    @PostMapping("/course")
    String postCourse(
            Course course,
            @AuthenticationPrincipal User user){
        courseService.save(course);
        return "redirect:/institution";
    }

}
