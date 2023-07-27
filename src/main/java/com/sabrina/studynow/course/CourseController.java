package com.sabrina.studynow.course;

import com.sabrina.studynow.token.APITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/course")
public class CourseController {

    @Autowired
    private ApplicationContext applicationContext;

    private final CourseService courseService;
    private final APITokenService apiTokenService;

    @Autowired
    public CourseController(CourseService courseService, APITokenService apiTokenService) {
        this.courseService = courseService;
        this.apiTokenService = apiTokenService;
    }

    @GetMapping
    public ResponseEntity<String> checkAPI() {
        return ResponseEntity.ok("Course API is working");
    }

    @PostMapping
    public ResponseEntity<String>
    addCourse(@RequestHeader("token") String token, @RequestBody Course course) {

        if (Objects.isNull(apiTokenService.getById(token)))
            return ResponseEntity.badRequest().body("Token is invalid");

        if (apiTokenService.getById(token).isExpired())
            return ResponseEntity.badRequest().body("Token is expired");

        courseService.add(course);

        return ResponseEntity.ok("Course added");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(MissingRequestHeaderException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> integrityViolationException(DataIntegrityViolationException e) {
        String message = e.getMessage();
        if(message.contains("Duplicate entry"))
            return ResponseEntity.badRequest().body("Duplicate entry exception: Course already exists");
        return ResponseEntity.badRequest().body(message);
    }

}
