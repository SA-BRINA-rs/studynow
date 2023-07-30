package com.sabrina.studynow.course.mode;

import com.sabrina.studynow.institution.InstitutionNullObject;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class ModeNullObject extends Mode {

    public ModeNullObject() {
        this.id = -1L;
        this.name = "";
        this.description = "";
        this.institution = new InstitutionNullObject();
    }
}
