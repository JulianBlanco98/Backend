package com.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Model.Pokemon;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String>{
	
	
}
