package com.sabrina.studynow.institution;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class InstitutionNullObject extends Institution {

    public InstitutionNullObject() {
        super();
        this.id = -1L;
        this.name = "";
        this.phone = "";
        this.description = "";
        this.thumbnail = null;
        this.tags = null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
