package com.Backend.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Model.Pokemon;
import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.PokemonMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pokemonTGC/pokemon")
public class PokemonController{
	
	
	private final PokemonService pokemonService;
	
	private final PokemonMapper pokemonMapper;

	@GetMapping("/{idPokemon}")
	public ResponseEntity<PokemonDTO> findByIdPokemon(@PathVariable final String idPokemon){
		
		final PokemonDTO pokemon = this.pokemonService.findByIdPokemon(idPokemon);		
		return ResponseEntity.status(200).body(pokemon);
	}
	
	@GetMapping("/filtro")
	public ResponseEntity<Map<String, Object>> findPokemonByFilter(@RequestParam Map<String, Object> filter) {
		
		System.out.println("Consulta de filtros: " + filter);
		final List<PokemonDTO> pokemons = this.pokemonService.findByFilters(filter);
		return ResponseEntity.status(200).body(Map.of("totalCards", pokemons.size(), "cards", pokemons));
		
	}
	

}
