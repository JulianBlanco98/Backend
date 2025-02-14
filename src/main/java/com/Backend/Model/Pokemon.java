package com.Backend.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pokemons")
@Data
public class Pokemon {

	@Id
	@Column(name = "idPokemon", nullable = false, unique = true)
	private String idPokemon;
	
	@Column(name = "setId", nullable = false)
	private String setId;
	
	@Column(name = "idNumber", nullable = false)
	private int idNumber;
	
	@Column(name = "pokemonName", nullable = false)
	private String pokemonName;
	
	@Column(name = "collectionPocket", nullable = false)
	private String collectionPocket;
	
	@Column(name = "deck")
	private String deck;
	
	@Column(name = "cardType", nullable = false)
	private String cardType;
	
	@Column(name = "cardColor")
	private String cardColor;
	
	@Column(name = "rarity", nullable = false) // (0 a 10)
	private Byte rarity;
	
	@Column(name = "cardStage", nullable = false)
	private String cardStage;
	
	@Column(name = "retreatCost", nullable = false) // (0 a 4)
	private Byte retreatCost;
	
	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;
	
	@Column(name = "hpPokemon", nullable = false) // (0 a 200)
	private Short hpPokemon;
	
	@Column(name = "textCards")
	private String textCards;
	
	@Column(name = "weakness")
	private String weakness;

	@ManyToMany
	@JoinTable(
		name = "pokemon_attack",
		joinColumns = @JoinColumn(name = "pokemon_id"),
		inverseJoinColumns = @JoinColumn(name = "attack_id")
	)
	private List<Attack> attack;
	
	@ManyToMany
	@JoinTable(
			name = "pokemon_ability",
			joinColumns = @JoinColumn(name = "pokemon_id"),
			inverseJoinColumns = @JoinColumn(name = "ability_id")
	)
	private List<Ability> ability;
	
	@ManyToMany
	@JoinTable(
			name = "pokemon_cardsrelated",
			joinColumns = @JoinColumn(name = "pokemon_id"),
			inverseJoinColumns = @JoinColumn(name = "cardsrelated_id")
	)
	private List<CardsRelated> cardsrelated;
	
}
