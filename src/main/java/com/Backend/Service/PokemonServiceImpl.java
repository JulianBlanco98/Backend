package com.Backend.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Backend.Model.Pokemon;
import com.Backend.Repository.PokemonRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PokemonServiceImpl implements PokemonService{

	private final PokemonRepository pokemonRepository;

	@Override
	public List<Pokemon> findAll() {
		
		return null;
	}
	
}
