package com.Backend.Service;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.Backend.Model.Pokemon;
import com.Backend.Repository.PokemonRepository;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.PokemonMapper;
import com.Backend.specification.PokemonSpecification;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PokemonServiceImpl implements PokemonService{

	private final PokemonRepository pokemonRepository;
	
	private final PokemonMapper pokemonMapper;

	@Override
	public List<PokemonDTO> findAll() {
		
		
		List<Pokemon> pokemons = this.pokemonRepository.findAll();
		if(pokemons.isEmpty()) {
			throw new EntityNotFoundException("la tabla de Pokémon está vacía");
		}
	
		List<PokemonDTO> pokemonDTOs = pokemons.stream()
				.map(this.pokemonMapper::entityToPokemonDTO)
				.toList();
		
		return pokemonDTOs;
	}

	@Override
	public List<PokemonDTO> findByPokemonName(String namePokemon) {
		
		List<Pokemon> pokemons = this.pokemonRepository.findByPokemonNameContains(namePokemon);
		if(pokemons.isEmpty()) {
			throw new EntityNotFoundException("No se encontro ningún pokemon con este nombre: " + namePokemon);
		}
		
	    return pokemons.stream()
	            .map(this.pokemonMapper::entityToPokemonDTO)
	            .toList();
		
	}

	@Override
	public PokemonDTO findByIdPokemon(String idPokemon) {
		
		return this.pokemonMapper.entityToPokemonDTO(this.pokemonRepository.findByIdPokemon(idPokemon).orElseThrow(() -> new EntityNotFoundException("Este id no coincide con ningún pokemon: " +idPokemon)));
	}

	@Override
	public List<PokemonDTO> findByFilters(Map<String, Object> filters) {
		
		Specification<Pokemon> spec = new PokemonSpecification(filters);
		List<Pokemon> pokemons = this.pokemonRepository.findAll(spec);
		if(pokemons.isEmpty()) {
			throw new EntityNotFoundException("No se encontro ningún pokemon con este filtro");
		}
		
		return pokemons.stream()
				.map(pokemonMapper::entityToPokemonDTO)
				.toList();	
	}
	
	
	
}
