package com.backend.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representa la colección de cartas del usuario.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Table(name = "card_user_collection")
@Data
@Entity
public class CardUserCollection {

	/**
	 * Identificador de la colección del usuario. Clave primaria autogenerada.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Email del usuario. Clave foránea de usuario.
	 */
	@ManyToOne
	@JoinColumn(name = "user_email", nullable = false)
	private User user;

	/**
	 * Todas las cartas que posee el usuario.
	 * Relación 1 - N
	 * @see UserCards
	 */
	@OneToMany(mappedBy = "cardUserCollection", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCards> cards;
	
	
}
