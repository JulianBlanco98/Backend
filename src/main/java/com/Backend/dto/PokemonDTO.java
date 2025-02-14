package com.Backend.dto;

import java.util.List;

import com.Backend.Model.CardsRelated;

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
	private Byte rarity;
	private String cardStage;
	private Byte retreatCost;
	private String imageUrl;
	private Short hpPokemon;
	private String textCards;
	private String weakness;

	private List<AbilityDTO> ability;
	private List<AttackDTO> attack;
	private List<CardsRelated> cardsrelated;

	@Data
	public static class AttackDTO {
		private String info;
		private String effect;
	}

	@Data
	public static class AbilityDTO {
		private String info;
		private String effect;
	}

}