package com.Backend.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Model.Pokemon;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.PokemonMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pokemonTGC/pokemon")
public class PokemonController{
	
	private final PokemonRepository pokemonRepository;
	
	private final PokemonMapper pokemonMapper;

	
	

}
