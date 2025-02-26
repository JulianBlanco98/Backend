package com.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.Pokemon;
import com.Backend.Model.UserCards;
import com.Backend.Model.UserCards.CardCategory;
import com.Backend.Service.PokemonService;
import com.Backend.dto.CardUserCollectionDTO;
import com.Backend.dto.PokemonCollectionDTO;
import com.Backend.dto.UserCardsDTO;
import com.Backend.mapper.CardUserCollectionMapper;
import com.Backend.mapper.PokemonMapper;
import com.Backend.mapper.UserCardsMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BackendApplication_CardUserCollection_Mappers {

	@Autowired
	private CardUserCollectionMapper collectionMapper;
	
	@Autowired
	private UserCardsMapper userCardsMapper;
	
	@Autowired
	private PokemonMapper pokemonMapper;
	
	private CardUserCollectionDTO cardUserCollectionDTO_test;
	private CardUserCollection cardUserCollection_test;

	@Autowired
	private PokemonService pokemonService;
	
	@BeforeEach
	void setUp() {
		
		// UserCardsDTO_test: carta de Bulbasaur
		UserCardsDTO userCardsDTO_test_1 = new UserCardsDTO();
		userCardsDTO_test_1.setId(1L);
		userCardsDTO_test_1.setUser_collection_id(1L);
		userCardsDTO_test_1.setCardId("A1-001");
		userCardsDTO_test_1.setCategory(CardCategory.Genetic);
		userCardsDTO_test_1.setHasTheCard(true);
		// UserCardsDTO_test: carta de Charizard Ex
		UserCardsDTO userCardsDTO_test_2 = new UserCardsDTO();
		userCardsDTO_test_2.setId(2L);
		userCardsDTO_test_2.setUser_collection_id(1L);
		userCardsDTO_test_2.setCardId("A1-004");
		userCardsDTO_test_2.setCategory(CardCategory.Genetic);
		userCardsDTO_test_2.setHasTheCard(true);
		
		List<UserCardsDTO> userCardsList = new ArrayList<>();
		userCardsList.add(userCardsDTO_test_1);
		userCardsList.add(userCardsDTO_test_2);
		
		// Crear la colección
		cardUserCollectionDTO_test = new CardUserCollectionDTO();
		cardUserCollectionDTO_test.setId(1L);
		cardUserCollectionDTO_test.setUserEmail("prueba@gmail.com");
		cardUserCollectionDTO_test.setCards(userCardsList);
		
		PokemonCollectionDTO pokemon1 = this.pokemonService.findByIdPokemonCollection("A1-001");
		PokemonCollectionDTO pokemon2 = this.pokemonService.findByIdPokemonCollection("A1-004");
		
		
		UserCards userCard1 = new UserCards();
        userCard1.setId(1L);
        userCard1.setPokemon(this.pokemonMapper.toEntityCollection(pokemon1));
        userCard1.setCategory(CardCategory.Genetic);
        userCard1.setHasTheCard(true);
        UserCards userCard2 = new UserCards();
        userCard1.setId(2L);
        userCard1.setPokemon(this.pokemonMapper.toEntityCollection(pokemon2));
        userCard1.setCategory(CardCategory.Genetic);
        userCard1.setHasTheCard(true);

        Set<UserCards> userCardsEntities = new HashSet<>();
        userCardsEntities.add(userCard1);
        userCardsEntities.add(userCard2);

        // Crear CardUserCollection Entity de prueba
        cardUserCollection_test = new CardUserCollection();
        cardUserCollection_test.setId(1L);
        cardUserCollection_test.setCards(userCardsEntities);
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void testMap_collectionDTOToEntity() {
		
		CardUserCollection entity = this.collectionMapper.toEntity(cardUserCollectionDTO_test);
		assertNotNull(entity);
		assertEquals(cardUserCollectionDTO_test.getId(), entity.getId());
		assertEquals(cardUserCollectionDTO_test.getUserEmail(), entity.getUser().getEmail());
		assertEquals(cardUserCollectionDTO_test.getCards().size(), entity.getCards().size());
			
	}
	
	@Test
	void testMap_collectionEntityToDTO() {
		
		CardUserCollectionDTO dto = this.collectionMapper.toDTO(cardUserCollection_test);
		assertNotNull(dto);
		assertEquals(cardUserCollection_test.getId(), dto.getId());
//		assertEquals(cardUserCollection_test.getUser().getEmail(), dto.getUserEmail());
		
		System.out.println("Tamaño lista test: " +cardUserCollection_test.getCards().size());
		System.out.println("Tamaño lista dto: " +dto.getCards().size());
		
		assertEquals(cardUserCollection_test.getCards().size(), dto.getCards().size());	
		
		// Ordenamos por `cardId` para evitar errores de orden aleatorio en Set
	    dto.getCards().sort((c1, c2) -> c1.getCardId().compareTo(c2.getCardId()));
		
		assertEquals(dto.getCards().get(0).getCardId(), "A1-001");
		assertEquals(dto.getCards().get(1).getCardId(), "A1-004");
				
	}
	
}
