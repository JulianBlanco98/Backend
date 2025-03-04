package com.backend.service;

import java.util.List;
import java.util.Map;

import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.PokemonDTO;

public interface PokemonService {

	public List<PokemonDTO> findAll();
	public List<PokemonDTO> findByPokemonName(String name);
	public PokemonDTO findByIdPokemon(String idPokemon);
	public List<PokemonDTO> findByFilters(Map<String, Object> filters);
	public PokemonCollectionDTO findByIdPokemonCollection(String idPokemon);
	
	
}
