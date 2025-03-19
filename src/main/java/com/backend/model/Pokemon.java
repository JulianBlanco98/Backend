package com.backend.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Representa una entidad de Pokémon mapeada a la tabla "pokemons" en la base de datos.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Entity
@Table(name = "pokemons")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pokemon {

	/**
	 * Identificador del pokemon. Clave primaria, única y no nula.
	 */
	@Id
	@Column(name = "idPokemon", nullable = false, unique = true)
	@EqualsAndHashCode.Include
	private String idPokemon;

	/**
	 * Identificador del set al que pertenece el pokemon. Campo no nulo.
	 */
	@Column(name = "setId", nullable = false)
	private String setId;

	/**
	 * Identificador numérico del pokemon. Campo no nulo.
	 */
	@Column(name = "idNumber", nullable = false)
	private int idNumber;

	/**
	 * Nombre del pokemon. Campo no nulo.
	 */
	@Column(name = "pokemonName", nullable = false)
	private String pokemonName;

	/**
	 * Nombre de la colección del pokemon. Campo no nulo.
	 */
	@Column(name = "collectionPocket", nullable = false)
	private String collectionPocket;

	/**
	 * Mazo donde está el pokemon. Campo puede ser nulo.
	 */
	@Column(name = "deck")
	private String deck;

	/**
	 * Tipo de carta de pokemon (Pokemon, Item o Supporter). Campo no nulo.
	 */
	@Column(name = "cardType", nullable = false)
	private String cardType;

	/**
	 * Naturaleza del pokemon. Campo puede ser nulo.
	 */
	@Column(name = "cardColor")
	private String cardColor;

	/**
	 * Rareza del pokemon (1 a 10). Campo no nulo.
	 */
	@Column(name = "rarity", nullable = false) // (0 a 10)
	private int rarity;

	/**
	 * Fase del pokemon. Campo no nulo.
	 */
	@Column(name = "cardStage", nullable = false)
	private String cardStage;

	/**
	 * Coste de retirada del pokemon (0 a 4). Campo no nulo.
	 */
	@Column(name = "retreatCost", nullable = false) // (0 a 4)
	private int retreatCost;

	/**
	 * Url de la carta pokemon alojada en cloudinary. Campo no nulo.
	 */
	@Column(name = "imageUrl", nullable = false)
	private String imageUrl;

	/**
	 * Vida del pokemon. Campo no nulo.
	 */
	@Column(name = "hpPokemon", nullable = false) // (0 a 200)
	private int hpPokemon;

	/**
	 * Texto de información de las cartas. Campo puede ser nulo.
	 */
	@Column(name = "textCards")
	private String textCards;

	/**
	 * Debilidad de naturaleza del pokemon. Campo puede ser nulo.
	 */
	@Column(name = "weakness")
	private String weakness;

	/**
	 * Ataque/es del pokemon. Mapeado con la tabla pokemon_attack.
	 * Relación N - N.
	 * Campo puede ser nulo
	 * @see Attack
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name = "pokemon_attack",
		joinColumns = @JoinColumn(name = "pokemon_id"),
		inverseJoinColumns = @JoinColumn(name = "attack_id")
	)
	private Set<Attack> attack;

	/**
	 * Habilidad del pokemon. Mapeado con la tabla pokemon_ability.
	 * Relación N - N.
	 * Campo puede ser nulo
	 * @see Ability
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "pokemon_ability",
			joinColumns = @JoinColumn(name = "pokemon_id"),
			inverseJoinColumns = @JoinColumn(name = "ability_id")
	)
	private Set<Ability> ability;

	/**
	 * Pokemon relacionados con el actual pokemon. Mapeado con la tabla pokemon_cardsrelated.
	 * Relación N - N.
	 * Campo puede ser nulo
	 * @see CardsRelated
	 */
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "pokemon_cardsrelated",
			joinColumns = @JoinColumn(name = "pokemon_id"),
			inverseJoinColumns = @JoinColumn(name = "cardsrelated_id")
	)
	private Set<CardsRelated> cardsrelated;
	
}
