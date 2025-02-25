package com.Backend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Model.CardUserCollection;
import com.Backend.Service.PokemonService;
import com.Backend.dto.CardUserCollectionDTO;
import com.Backend.dto.CardUserCollectionDeatilsDTO;
import com.Backend.dto.PokemonDTO;
import com.Backend.mapper.CardUserCollectionMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BackendApplication_CardUserCollection_Mappers {

	@Autowired
	private CardUserCollectionMapper mapper;
	
	
	private CardUserCollectionDTO cardUserCollectionDTO_test;

	@Autowired
	private PokemonService pokemonService;
	
	@BeforeEach
	void setUp() {
		cardUserCollectionDTO_test = new CardUserCollectionDTO();
		cardUserCollectionDTO_test.setId((long) 1);
		cardUserCollectionDTO_test.setUserEmail("prueba@gmail.com");
		
		// Recuperar las cartas de la expansion PROMO
		Map<String, Object> filters_PROMO = new HashMap<>();
		filters_PROMO.put("deck", "PROMO");
		List<PokemonDTO> pokemon_PROMODeck = this.pokemonService.findByFilters(filters_PROMO);
		
		// Convertir la lista de PokemonDTO a CardUserCollectionDeatilsDTO
        List<CardUserCollectionDeatilsDTO> cardDetails = pokemon_PROMODeck.stream().map(pokemonDTO -> {
            CardUserCollectionDeatilsDTO detailDTO = new CardUserCollectionDeatilsDTO();
            detailDTO.setPokemonId(pokemonDTO.getIdPokemon());
            detailDTO.setCollectionType("PROMO");
            detailDTO.setHasTheCard(false); // Asumiendo que el usuario tiene la carta
            return detailDTO;
        }).toList();

        cardUserCollectionDTO_test.setCards(cardDetails);
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void testMap_collectionDTOToEntity() {
		
		CardUserCollection entity = this.mapper.toEntity(cardUserCollectionDTO_test);
		
		assertTrue(entity.getCards().size() == 41);
		
	}
	
}
