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

import java.util.*;

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

        if (user == null) return "redirect:/login";

        Institution institution = institutionService.getByUserId(user.getId());
        List<Mode> modes = modeService.getModesByInstitutionId(institution.getId())
                .orElse(Collections.emptyList());
        List<Course> courses = courseService.getAllCoursesByInstitutionId(institution.getId())
                .orElse(Collections.emptyList());

        model.addAttribute("user", user);
        model.addAttribute("institution", institution);
        model.addAttribute("modes", modes);
        model.addAttribute("courses", courses);
        model.addAttribute("pageName", "institution");

        return "institution";
    }

    @PostMapping
    String postInstitution(Model model, Institution institution, @AuthenticationPrincipal User user){

        institution.setUser(user);
        institutionService.save(institution).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("institution", institution);
        model.addAttribute("pageName", "institution");
        return "institution";
    }

    @PostMapping("/mode")
    String createMode(Mode mode, @AuthenticationPrincipal User user){
        try {
            Institution institution = institutionService.getByUserId(user.getId());
            long institutionId = institution.getId();
            String modeName = mode.getName();
            modeService.existsByNameAndInstitutionId(modeName, institutionId).ifPresent(aBoolean -> {
                if (aBoolean) throw new RuntimeException("Mode already exists.");
            });
            mode.setId(null);
            modeService.save(mode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/institution";
    }

    @DeleteMapping("/mode/{id}")
    ResponseEntity<String> deleteMode(@PathVariable("id") Long id, @AuthenticationPrincipal User user){

        List<Course> courses = Optional.of(courseService.getAllByModeId(id))
                .orElse(Collections.emptyList());

        for(Course course : courses){
            favoriteService.deleteAllByCourseId(course.getId());
            rateService.deleteAllByCourseId(course.getId());
        }

        courseService.deleteAllByModeId(id);
        modeService.delete(id);
        return ResponseEntity.ok("Mode deleted successfully.");
    }

    @PutMapping("/mode/{id}")
    ResponseEntity<?> putMode(@RequestBody Mode mode, @AuthenticationPrincipal User user){

        Map<String, String> response = new HashMap<>();
        try {
           modeService.getById(mode.getId())
                   .orElseThrow(() -> new RuntimeException("Mode not found."));
            modeService.save(mode);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Mode updated successfully.");
        response.put("url", "/institution");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/course")
    String postCourse(Course course, @AuthenticationPrincipal User user){

        try {
            Institution institution = institutionService.getByUserId(user.getId());
            courseService.existsByNameAndInstitutionId(course.getName(), institution.getId()).ifPresent(aBoolean -> {
                if (aBoolean) throw new RuntimeException("Course already exists.");
            });
            course.setId(null);
            courseService.save(course);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/institution";
    }

    @DeleteMapping("/course/{id}")
    ResponseEntity<String> deleteCourse(@PathVariable("id") Long id, @AuthenticationPrincipal User user){

        favoriteService.deleteAllByCourseId(id);
        rateService.deleteAllByCourseId(id);
        courseService.delete(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @PutMapping("/course")
    ResponseEntity<?> putCourse(@RequestBody Course course, @AuthenticationPrincipal User user){

        Map<String, String> response = new HashMap<>();
        try {
            courseService.getById(course.getId())
                    .orElseThrow(() -> new RuntimeException("Course not found."));
            courseService.update(course);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        response.put("message", "Course updated successfully.");
        response.put("url", "/institution");
        return ResponseEntity.ok(response);
    }

}
