package com.Backend.Model;

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

@Table(name = "card_user_collection")
@Data
@Entity
public class CardUserCollection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_email", nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "cardUserCollection", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CardUserCollectionDetail> cards;
	
	
}
