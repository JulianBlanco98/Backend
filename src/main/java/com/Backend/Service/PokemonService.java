package com.Backend.Service;

import java.util.List;

import com.Backend.Model.Pokemon;
import com.Backend.dto.PokemonDTO;

public interface PokemonService {

	List<PokemonDTO> findAll();
	
}
