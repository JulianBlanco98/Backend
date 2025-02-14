package com.Backend.Service;

import org.springframework.stereotype.Service;

import com.Backend.Repository.PokemonRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PokemonServiceImpl {

	private final PokemonRepository pokemonRepository;
	
}
