package com.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccordionDTO {
    private int deckCards;
    private int deckCardsUser;
    private List<PokemonAccordionDTO> filteredCards;
}
