package com.backend.dto;

import lombok.Data;

@Data
public class PokemonAccordionDTO {
    private String idPokemon;
    private String pokemonName;
    private boolean hasTheCard;
    private String deck;
    private String collectionPocket;
}
