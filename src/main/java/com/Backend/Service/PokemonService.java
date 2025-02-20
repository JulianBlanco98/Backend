package com.Backend.Service;

import java.util.List;
import java.util.Map;

import com.Backend.dto.PokemonDTO;

public interface PokemonService {

	public List<PokemonDTO> findAll();
	public List<PokemonDTO> findByPokemonName(String name);
	public PokemonDTO findByIdPokemon(String idPokemon);
	public List<PokemonDTO> findByFilters(Map<String, Object> filters);
	
	
}
