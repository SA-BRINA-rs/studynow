package com.sabrina.studynow.user;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class UserNullObject extends User {

    public UserNullObject() {
        super();
        this.id = -1L;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.userRole = UserRole.VISITOR;
        this.password = "";
    }
}
