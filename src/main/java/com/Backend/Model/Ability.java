package com.Backend.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ability")
@Data
public class Ability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "info", nullable = false)
	private String info;
	
	@Column(name = "effect")
	private String effect;
	
	@ManyToMany(mappedBy = "ability")
	private List<Pokemon> pokemons;

}
