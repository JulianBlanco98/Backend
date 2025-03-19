package com.backend.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representa la habilidad de un pokemon.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Entity
@Table(name = "ability")
@Data
public class Ability {

	/**
	 * Identificador de la habilidad. Clave primaria, autogenerada.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Nombre de la habilidad. Campo no nulo.
	 */
	@Column(name = "info", nullable = false)
	private String info;

	/**
	 * Efecto de la habilidad. Campo puede ser nulo.
	 */
	@Column(name = "effect")
	private String effect;
	
	@ManyToMany(mappedBy = "ability")
	private Set<Pokemon> pokemons;

}
