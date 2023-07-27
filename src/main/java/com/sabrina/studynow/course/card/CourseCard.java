package com.sabrina.studynow.course.card;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.course.Course;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Entity
@DiscriminatorValue("COURSE")
@Data @EqualsAndHashCode(callSuper = true)
public class CourseCard extends Course implements CardData {

    @Getter(AccessLevel.NONE)
    private final int DEFAULT_RATE = 1;

    @Override
    public String getCardID() {
        return Long.toString(id);
    }

    @Override
    public String getCardName() {
        return name;
    }

    @Override
    public String getCardDescription() {
        return description;
    }

    @Override
    public String getCardTextLink() {
        return "View course";
    }

    @Override
    public int getCardRate() {
        return Optional.ofNullable(averageRate)
                .orElse(DEFAULT_RATE);
    }

    @Override
    public String getCardImage() {
        return "";
    }
}
