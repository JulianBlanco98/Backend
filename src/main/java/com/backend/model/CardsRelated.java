package com.backend.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representa los pokemon relacionados con uno en específico.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Entity
@Table(name = "cardsrelated")
@Data
public class CardsRelated {

	/**
	 * Identificador del IdPokemon relacionado. Clave primaria.
	 */
	@Id
    private String id;
	
	@ManyToMany(mappedBy = "cardsrelated")
	private Set<Pokemon> pokemons;

}
