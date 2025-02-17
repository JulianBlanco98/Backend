package com.Backend.runner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PreConfigServer implements CommandLineRunner{

	private final PokemonService pokemonService;
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Inicializar la tabla de pokemon si está vacía");
		System.out.println("--------------------------------------------------------------------------------------");
		
		try {
			List<PokemonDTO> pokemons = pokemonService.findAll();
			
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Hora de inicializar datos...");
		}
		
		
		
	}

	
	
	
}
