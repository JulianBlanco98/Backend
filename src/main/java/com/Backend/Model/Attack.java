package com.Backend.Model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "attack")
@Data
public class Attack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "info", nullable = false)
	private String info;
	
	@Column(name = "effect")
	private String effect;
	
	@ManyToMany(mappedBy = "attack")
	private Set<Pokemon> pokemons;
	
}
