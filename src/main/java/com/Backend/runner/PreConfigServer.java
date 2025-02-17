package com.Backend.runner;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			ObjectMapper mapper = new ObjectMapper();
			try {
				
				InputStream input = new ClassPathResource("db/apiJSON_pokemon2.json").getInputStream();
				List<PokemonDTO> pokemonListJSON = mapper.readValue(input, new TypeReference<List<PokemonDTO>>(){});
				
				System.out.println(pokemonListJSON);
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
	}

	
	
	
}
