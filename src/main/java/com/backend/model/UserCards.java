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


@Table(name = "user_cards")
@Data
@Entity
public class UserCards {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_collection_id", nullable = false)
	private CardUserCollection cardUserCollection;
	
	@ManyToOne
	@JoinColumn(name = "cardId", nullable = false)
	private Pokemon pokemon;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CardCategory category;
	
	@Column(name = "hasTheCard", nullable = false)
	private boolean hasTheCard;
	
	
	public static enum CardCategory {
		Genetic, PROMO, Mythical, Smackdown
	}
	
}
