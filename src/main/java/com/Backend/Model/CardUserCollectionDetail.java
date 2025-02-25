package com.Backend.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Table(name = "card_user_collection_detail")
@Data
@Entity
public class CardUserCollectionDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "collection_id", nullable = false)
	private CardUserCollection cardUserCollection;
	
	@ManyToOne
	@JoinColumn(name = "pokemon_id", nullable = false)
	private Pokemon pokemon;
	
	@Column(name= "has_the_card", nullable = false)
	private boolean hasTheCard;
	
	@Column(name = "collection_type", nullable = false)
	private String collectionType; // Genetic, PROMO, Mythical, Smackdown
	
	
}
