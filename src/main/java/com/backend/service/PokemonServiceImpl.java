package com.backend.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.backend.model.Pokemon;
import com.backend.repository.PokemonRepository;
import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.PokemonDTO;
import com.backend.mapper.PokemonMapper;
import com.backend.specification.PokemonSpecification;

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
	
		return pokemons.stream()
				.map(this.pokemonMapper::entityToPokemonDTO)
				.toList();
		
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

	@Override
	public PokemonCollectionDTO findByIdPokemonCollection(String idPokemon) {
		
		
		Pokemon pokemon = this.pokemonRepository.findByIdPokemon(idPokemon)
	            .orElseThrow(() -> new EntityNotFoundException("Este id no coincide con ningún pokemon: " + idPokemon));
		
//		return this.pokemonMapper.toEntityCollection(this.pokemonRepository.findByIdPokemon(idPokemon).orElseThrow(() -> new EntityNotFoundException("Este id no coincide con ningún pokemon: " +idPokemon)));
		
		return this.pokemonMapper.toPokemonCollectionDTO(pokemon);
	}
	
	
	
}
