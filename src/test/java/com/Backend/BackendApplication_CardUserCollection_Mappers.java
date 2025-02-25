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
import com.Backend.dto.UserCardsDTO;
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
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void testMap_collectionDTOToEntity() {
		
		
	}
	
}
