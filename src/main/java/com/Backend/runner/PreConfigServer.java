package com.Backend.runner;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.Backend.Model.Pokemon;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.PokemonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PreConfigServer implements CommandLineRunner {


	private final PokemonRepository pokemonRepository;

	private final PokemonMapper pokemonMapper;

	@Override
	public void run(String... args) throws Exception {

		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Inicializar la tabla de pokemon si está vacía");
		System.out.println("--------------------------------------------------------------------------------------");
		
		if(this.pokemonRepository.count() == 0) {
			
			System.out.println("Hora de inicializar datos...");
			ObjectMapper mapper = new ObjectMapper();
			try {
				
				InputStream input = new ClassPathResource("db/apiJSON_pokemon2.json").getInputStream();
				List<PokemonDTO> pokemonListJSON = mapper.readValue(input, new TypeReference<List<PokemonDTO>>() {
				});
				
				for (PokemonDTO poke : pokemonListJSON) {
//					System.out.println(poke);
					Pokemon pokemoToSave = this.pokemonMapper.pokemonDTOTOEntity(poke);
					this.pokemonRepository.save(pokemoToSave);
				}
				
				System.out.println("Datos iniciados correctamente");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
