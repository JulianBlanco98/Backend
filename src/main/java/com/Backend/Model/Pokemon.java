package com.Backend.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
