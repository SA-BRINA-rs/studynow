package com.sabrina.studynow.course.card;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.course.Course;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Optional;

@Entity
@DiscriminatorValue("COURSE")
@Data @EqualsAndHashCode(callSuper = true)
public class CourseCard extends Course implements CardData {

    @Transient
    @Getter(AccessLevel.NONE)
    private final int DEFAULT_RATE = 1;

    @Override
    public Long getCardID() {
        return id;
    }

    @Override
    public String getCardName() {
        return name;
    }

    @Override
    public String getCardSubtitle() {
        if(institution == null) return "";
        return institution.getName();
    }

    @Override
    public String getCardDescription() {
        return description;
    }

    @Override
    public String getCardLabel() {
        return "€ " + price;
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
    public void setAvgCardRate(int rate) {
        averageRate = rate;
    }

    @Override
    public String getCardImage() {
        return "";
    }

    @Override
    public String getURL() {
        return "view";
    }
}
