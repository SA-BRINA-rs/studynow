package com.sabrina.studynow.base.card;

public interface CardData {
    Long getCardID();
    String getCardName();
    String getCardSubtitle();
    String getCardDescription();
    String getCardLabel();
    String getCardTextLink();
    int getCardRate();
    void setAvgCardRate(int rate);
    String getCardImage();
    String getURL();
}
