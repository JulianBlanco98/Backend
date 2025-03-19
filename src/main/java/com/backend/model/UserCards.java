package com.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Representa la carta de la colección de un usuario.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Table(name = "user_cards")
@Data
@Entity
public class UserCards {
	/**
	 * Identificador de la carta del usuario. Clave primaria autogenerada.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Identificado de la colección del usuario. Clave foránea de card_user_collection
	 * @see CardUserCollection
	 */
	@ManyToOne
	@JoinColumn(name = "user_collection_id", nullable = false)
	private CardUserCollection cardUserCollection;

	/**
	 * Identificador del pokemon. Clave foránea de pokemons. Campo no nulo.
	 * @see Pokemon
	 */
	@ManyToOne
	@JoinColumn(name = "cardId", nullable = false)
	private Pokemon pokemon;

	/**
	 * Expansión de la carta. Campo no nulo.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CardCategory category;

	/**
	 * Indica si el usuario tiene esta carta o no. Campo no nulo.
	 */
	@Column(name = "hasTheCard", nullable = false)
	private boolean hasTheCard;

	/**
	 * 4 colecciones que hay en TGCP. {Gentic, PROMO, Myhtical, Smackdown}
	 */
	public static enum CardCategory {
		Genetic, PROMO, Mythical, Smackdown
	}
	
}
