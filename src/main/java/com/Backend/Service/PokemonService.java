package com.Backend.Service;

import java.util.List;
import com.Backend.dto.PokemonDTO;

public interface PokemonService {

	public List<PokemonDTO> findAll();
	public List<PokemonDTO> findByPokemonName(String name);
	public PokemonDTO findByIdPokemon(String idPokemon);
	
	
}
