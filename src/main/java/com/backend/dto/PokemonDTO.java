package com.backend.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PokemonDTO {

	private String idPokemon;
	private String setId;
	private int idNumber;
	private String pokemonName;
	private String collectionPocket;
	private String deck;
	private String cardType;
	private String cardColor;
	private int rarity;
	private String cardStage;
	private int retreatCost;
	private String imageUrl;
	private int hpPokemon;
	private String textCards;
	private String weakness;

	private List<AbilityDTO> ability;
	private List<AttackDTO> attack;
	
	@JsonProperty("cardsRelated")
	private List<CardsRelatedDTO> cardsrelated;

	@Data
	public static class AttackDTO {
	    private int id;
	    private String info;
	    private String effect;
	}

	@Data
	public static class AbilityDTO {
	    private int id;
	    private String info;
	    private String effect;
	}

	@Data
	public static class CardsRelatedDTO {
	    private String id;
	}

}