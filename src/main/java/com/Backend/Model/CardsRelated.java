package com.Backend.Model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cardsrelated")
@Data
public class CardsRelated {
	
	@Id
    private String id;
	
	@ManyToMany(mappedBy = "cardsrelated")
	private List<Pokemon> pokemons;

}
