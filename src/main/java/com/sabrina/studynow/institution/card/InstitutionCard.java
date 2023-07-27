package com.sabrina.studynow.institution.card;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.institution.Institution;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Optional;

@Entity
@DiscriminatorValue("INSTITUTION")
@Data @EqualsAndHashCode(callSuper = true)
public class InstitutionCard extends Institution implements CardData {

    @Transient
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
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
        return "Browser courses";
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