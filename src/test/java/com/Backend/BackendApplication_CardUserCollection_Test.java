package com.Backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.Pokemon;
import com.Backend.Model.User;
import com.Backend.Model.UserCards;
import com.Backend.Model.UserCards.CardCategory;
import com.Backend.Model.User.Role;
import com.Backend.Repository.CardUserCollectionRepository;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Repository.UserCardsRepository;
import com.Backend.Repository.UserRepository;
import com.Backend.Service.CardUserCollectionService;
import com.Backend.Service.UserService;
import com.Backend.dto.ListCardsUserCardsUpdateDTO;
import com.Backend.dto.PokemonCollectionDTO;
import com.Backend.dto.UserCardsDTO;
import com.Backend.dto.UserDTO;
import com.Backend.mapper.UserCardsMapper;
import com.Backend.mapper.UserMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BackendApplication_CardUserCollection_Test {
	
	// CardUserCollection y UserCards 
	@Autowired private CardUserCollectionService cardUserCollectionService;
	@Autowired private CardUserCollectionRepository cardUserCollectionRepository;
	@Autowired private UserCardsRepository userCardsRepository;
	@Autowired private UserCardsMapper userCardsMapper;
	
	// Usuarios
	@Autowired private UserService userService;
	@Autowired private UserMapper userMapper;
	@Autowired private UserRepository userRepository;
	
	// Pokemon
	@Autowired private PokemonRepository pokemonRepository;
	
	// Objetos
	private UserDTO userDTOTest;
	private CardUserCollection collection_test;
	
	@BeforeEach
	void setUp() {
		
		// Crear un usuario y registrarlo en la BD, pero comprobar primero que no existe en la BD (problemas)
		Optional<User> isUser = this.userRepository.findByEmail("test@test.com");
		if(isUser.isPresent()) {
			this.userRepository.delete(isUser.get());
		}
		
		this.userDTOTest = new UserDTO();
		this.userDTOTest.setEmail("test@test.com");
		this.userDTOTest.setUserName("userName_test");
		this.userDTOTest.setLastName("lastName_test");
		this.userDTOTest.setNickName("nickName_test");
		this.userDTOTest.setDateOfBirth(new Date());
		this.userDTOTest.setRol(Role.user);
		this.userDTOTest.setPassword("1234");
		this.userService.registerUser(userDTOTest);
		
		// Añadirle una tabla de colección y guardarlo en la BD
		this.collection_test = new CardUserCollection();
		collection_test.setUser(this.userMapper.userDTOToEntity(userDTOTest));
		this.cardUserCollectionRepository.save(collection_test);
		
		// Voy a añadirle la expansión de Genetic y PROMO
		List<Pokemon> geneticCollection = this.pokemonRepository.findBySetId("A1");
		List<Pokemon> promoCollection = this.pokemonRepository.findBySetId("PROMO");
		
		List<UserCards> userCards1 = geneticCollection.stream().map(pokemon -> {
			UserCards card = new UserCards();
			card.setCardUserCollection(collection_test);
			card.setPokemon(pokemon);
			card.setCategory(CardCategory.Genetic);
			card.setHasTheCard(false);
			return card;
		}).toList();
		this.userCardsRepository.saveAll(userCards1);
		
		List<UserCards> userCards2 = promoCollection.stream().map(pokemon -> {
			UserCards card = new UserCards();
			card.setCardUserCollection(collection_test);
			card.setPokemon(pokemon);
			card.setCategory(CardCategory.PROMO);
			card.setHasTheCard(false);
			return card;
		}).toList();		
		this.userCardsRepository.saveAll(userCards2);
		
		
//		List<UserCards> allCards = this.userCardsRepository.findByCardUserCollection(collection_test);
//		log.info("Cartas guardadas en BD: {}", allCards.size());
		
	}
	
	@AfterEach
	void tearDown() {
		
		if(collection_test != null) {
			
			// Borrar las userCards dependiendo del parámetro CardUserCollection
			List<UserCards> userCards = this.userCardsRepository.findByCardUserCollection(collection_test);
			this.userCardsRepository.deleteAll(userCards);
			
			// Borrar el cardUserCollection
			this.cardUserCollectionRepository.delete(collection_test);
			
		}
		
		// Borrar el usuario
		this.userRepository.delete(this.userMapper.userDTOToEntity(userDTOTest));
	}
	
	@Test
	void test_isCollectionInitialized_IncorrectGmail() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			this.cardUserCollectionService.isCollectionInitialized("test2@test.com", "Genetic");
		});
		
	}
	@Test
	void test_isCollectionInitialized_IncorrectExpansion() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			this.cardUserCollectionService.isCollectionInitialized("test@test.com", "sdsdsdsd");
		});
		
	}
	@Test
	void test_isCollectionInitialized() {
		
		assertTrue(this.cardUserCollectionService.isCollectionInitialized("test@test.com", "Genetic"));
		assertTrue(this.cardUserCollectionService.isCollectionInitialized("test@test.com", "PROMO"));
	}
	
	@Test
	void test_initCollection_incorrectExpansion() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			this.cardUserCollectionService.initializeCollection("test@test.com", "adasdasda");
		});
	}
	@Test
	void test_initCollection() {
		
		this.cardUserCollectionService.initializeCollection("test@test.com", "Mythical");
		List<PokemonCollectionDTO> mythicalCards = this.cardUserCollectionService.getUserExpansionCards("test@test.com", "Mythical");
		
		assertNotNull(mythicalCards);
		assertTrue(mythicalCards.size() == 86);
		
	}
	
	@Transactional
	@Test
	void test_updateCollection() {
		ListCardsUserCardsUpdateDTO updatedCards = new ListCardsUserCardsUpdateDTO();
//		List<UserCards> promoCards = this.userCardsRepository.findByCardUserCollectionAndCategory(collection_test, CardCategory.PROMO);
		List<UserCards> promoCards = new ArrayList<>();
		try {
			promoCards = this.userCardsRepository.findByCardUserCollectionAndCategory(collection_test, CardCategory.PROMO);
		    log.info("Cartas recuperadas: {}", promoCards.size());
		} catch (Exception e) {
		    log.error("Error al recuperar cartas: ", e);
		}
		
		assertTrue(promoCards.size() >= 6);
		
		// 5 primeras a true
		for(int i=0; i< 5; i++) {
			promoCards.get(i).setHasTheCard(true);
		}
		
		List<UserCardsDTO> promoCardsDTO = promoCards.stream()
				.map(this.userCardsMapper::toDTO)
				.toList();
		
		updatedCards.setCards(promoCardsDTO);
		
		this.cardUserCollectionService.updateUserCollection("test@test.com", "PROMO", updatedCards);
		
		List<UserCards> updatedPromoCards = this.userCardsRepository.findByCardUserCollectionAndCategory(collection_test, CardCategory.PROMO);
		
		assertTrue(promoCards.size() >= 6);
		for(int i=0; i< 5; i++) {
			assertTrue(updatedPromoCards.get(i).isHasTheCard());
		}
		assertTrue(!updatedPromoCards.get(5).isHasTheCard(), "La carta 5 tiene que ser falsa");		
		
	}
	
	

}
