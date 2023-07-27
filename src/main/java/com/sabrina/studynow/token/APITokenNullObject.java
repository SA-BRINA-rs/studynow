package com.sabrina.studynow.token;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @ToString
public class APITokenNullObject extends APIToken {

    public APITokenNullObject() {
        this.active = false;
        this.expirationDate = LocalDate.now();
        this.neverExpires = false;
        this.id = "";
    }

}
