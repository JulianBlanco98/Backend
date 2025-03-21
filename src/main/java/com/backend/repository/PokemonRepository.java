package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.backend.model.Pokemon;

import java.util.List;


@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String>, JpaSpecificationExecutor<Pokemon>{
	
//	@Query("SELECT p from Pokemon p WHERE p.pokemonName LIKE %?1%")
	@EntityGraph(attributePaths = {"attack", "ability", "cardsrelated"})
	List<Pokemon> findByPokemonNameContains(String pokemonName);
	
	@EntityGraph(attributePaths = {"attack", "ability", "cardsrelated"})
	Optional<Pokemon> findByIdPokemon(String idPokemon);
	
	List<Pokemon> findBySetId(String setId);

	Long countBySetId(String setId);
}
