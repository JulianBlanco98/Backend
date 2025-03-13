package com.backend.dto;

import lombok.Data;

@Data
public class DashboardUserCollectionDTO {
    private int cardsGenetic;
    private int cardsGeneticUser;
    private int cardsMythical;
    private int cardsMythicalUser;
    private int cardsPROMO;
    private int cardsPROMOUser;
    private int cardsSmackdown;
    private int cardsSmackdownUser;
    private int totalCards;
    private int totalCardsUser;
}
