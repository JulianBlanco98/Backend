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
 * Representa el ataque de un pokemon.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Entity
@Table(name = "attack")
@Data
public class Attack {

	/**
	 * Identificador del ataque. Clave primaria, autogenerada.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Nombre del ataque. Campo no nulo.
	 */
	@Column(name = "info", nullable = false)
	private String info;

	/**
	 * Efecto del ataque. Campo puede ser nulo.
	 */
	@Column(name = "effect")
	private String effect;
	
	@ManyToMany(mappedBy = "attack")
	private Set<Pokemon> pokemons;
	
}
