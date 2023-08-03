package com.sabrina.studynow.course.filter;

import com.sabrina.studynow.course.Course;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder @Data @EqualsAndHashCode
public class CourseSearch {

    private Course course;
    private double maxPrice;
}
