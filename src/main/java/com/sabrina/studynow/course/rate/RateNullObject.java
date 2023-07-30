package com.sabrina.studynow.course.rate;

import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.course.CourseNullObject;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserNullObject;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public final class RateNullObject extends Rate {
    public RateNullObject() {
        this.id = -1L;
        this.user = new UserNullObject();
        this.course = new CourseNullObject();
        this.rate = 1;
        this.comment = "";
    }
}
