package com.sabrina.studynow.course.favorite;

import com.sabrina.studynow.course.Course;
import com.sabrina.studynow.user.User;
import com.sabrina.studynow.user.UserNullObject;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public final class FavoriteNullObject extends Favorite {

        public FavoriteNullObject() {
            this.id = -1L;
            this.user = new UserNullObject();
            this.course = Course.builder().id(-1L).build();
        }

}
