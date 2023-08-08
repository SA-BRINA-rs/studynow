package com.sabrina.studynow.cards;

import com.sabrina.studynow.base.card.CardData;
import com.sabrina.studynow.course.card.CourseCard;
import com.sabrina.studynow.institution.card.InstitutionCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    @Test
    public void testGetCars() {

        List<CourseCard> courseCards = new ArrayList<>();
        CourseCard courseCard = new CourseCard();
        courseCard.setId(1L);
        courseCard.setName("Test Name");
        courseCard.setDescription("Test Description");
        courseCard.setPrice(100);
        courseCards.add(courseCard);

        List<InstitutionCard> institutionCards = new ArrayList<>();
        InstitutionCard institutionCard = new InstitutionCard();
        institutionCard.setId(1L);
        institutionCard.setName("Test Name");
        institutionCard.setDescription("Test Description");
        institutionCards.add(institutionCard);

        List<? extends CardData> cards1 = getCourseCards(courseCards);
        List<? extends CardData> cards2 = getInstitutionCards(institutionCards);

        for (int i=0; i < cards1.size(); i++){
            assertEquals(cards1.get(i).getCardID(), cards2.get(i).getCardID());
            assertEquals(cards1.get(i).getCardName(), cards2.get(i).getCardName());
            assertEquals(cards1.get(i).getCardSubtitle(), cards2.get(i).getCardSubtitle());
            assertEquals(cards1.get(i).getCardDescription(), cards2.get(i).getCardDescription());
        }

    }

    public static List<? extends CardData> getInstitutionCards(List<InstitutionCard> cards) {
        return cards;
    }

    public static List<? extends CardData> getCourseCards(List<CourseCard> cards) {
        return cards;
    }
}
