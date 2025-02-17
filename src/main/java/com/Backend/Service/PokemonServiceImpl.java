package com.Backend.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Backend.Model.Pokemon;
import com.Backend.Repository.PokemonRepository;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.PokemonMapper;

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
				.collect(Collectors.toList());
		
		return pokemonDTOs;
	}
	
}
