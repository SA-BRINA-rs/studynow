package com.sabrina.studynow.course;

import com.sabrina.studynow.course.mode.ModeNullObject;
import com.sabrina.studynow.institution.InstitutionNullObject;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;

@Getter @ToString
public class CourseNullObject extends Course {

    public CourseNullObject() {
        this.id = -1L;
        this.name = "";
        this.subject = "";
        this.description = "";
        this.averageRate = 1;
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now();
        this.price = 0.0;
        this.tags = Collections.emptyList();
        this.thumbnail = new byte[0];
        this.mode = new ModeNullObject();
        this.institution = new InstitutionNullObject();
    }
}
