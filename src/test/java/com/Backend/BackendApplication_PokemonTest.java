package com.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Model.Ability;
import com.Backend.Model.Attack;
import com.Backend.Model.CardsRelated;
import com.Backend.Model.Pokemon;
import com.Backend.Service.PokemonService;
import com.Backend.dto.PokemonDTO;
import com.Backend.dto.PokemonDTO.AbilityDTO;
import com.Backend.dto.PokemonDTO.AttackDTO;
import com.Backend.dto.PokemonDTO.CardsRelatedDTO;
import com.Backend.mapper.PokemonMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
class BackendApplication_PokemonTest {

	@Autowired
	private PokemonMapper pokemonMapper;
	
	@Autowired
	private PokemonService pokemonService;
	
	private PokemonDTO pokemonDTO_Test;
	private PokemonDTO pokemonDTO_BD;
	
	@BeforeEach
	void setUp() {
		pokemonDTO_Test = new PokemonDTO();
		pokemonDTO_Test.setIdPokemon("A1-001");
		pokemonDTO_Test.setSetId("A1");
		pokemonDTO_Test.setIdNumber(1);
		pokemonDTO_Test.setPokemonName("Bulbasaur");
		pokemonDTO_Test.setCollectionPocket("Genetic Apex");
		pokemonDTO_Test.setDeck("A1M");
		pokemonDTO_Test.setCardType("Pokemom");
		pokemonDTO_Test.setCardColor("Grass");
		pokemonDTO_Test.setRarity(1);
		pokemonDTO_Test.setCardStage("Basic");
		pokemonDTO_Test.setRetreatCost(1);
		pokemonDTO_Test.setImageUrl("www.prueba.com");
		pokemonDTO_Test.setCardsrelated(null);
		pokemonDTO_Test.setHpPokemon(70);
		// Ataque
        List<AttackDTO> attacks = new ArrayList<>();
        AttackDTO attack = new AttackDTO();
        attack.setId(1);
        attack.setInfo("{GC} Vine Whip 40");
        attack.setEffect(null);
        attacks.add(attack);
        pokemonDTO_Test.setAttack(attacks);

        // Habilidad
        List<AbilityDTO> abilities = new ArrayList<>();
        pokemonDTO_Test.setAbility(abilities);

        // Cards relacionadas
        List<CardsRelatedDTO> relatedCards = new ArrayList<>();
        pokemonDTO_Test.setCardsrelated(relatedCards);
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void testMap_pokemonDTOTOEntity() {
		Pokemon pokemonEntity = this.pokemonMapper.pokemonDTOTOEntity(pokemonDTO_Test);
		
		assertEquals(pokemonEntity.getIdPokemon(), this.pokemonDTO_Test.getIdPokemon());
		assertEquals(pokemonEntity.getSetId(), this.pokemonDTO_Test.getSetId());
		assertEquals(pokemonEntity.getIdNumber(), this.pokemonDTO_Test.getIdNumber());
		assertEquals("Bulbasaur", this.pokemonDTO_Test.getPokemonName());
		
		//Ataques: lista de DTO vs lista entity. Recoorer por si hay 2 ataques
		List<AttackDTO> dtoAttacks = this.pokemonDTO_Test.getAttack();
		List<Attack> expectedAttacks = pokemonEntity.getAttack().stream().toList();
		assertEquals(dtoAttacks.size(), expectedAttacks.size());
		
		for (int i = 0; i < expectedAttacks.size(); i++) {

			assertEquals(dtoAttacks.get(i).getInfo(), expectedAttacks.get(i).getInfo());
			assertEquals(dtoAttacks.get(i).getEffect(), expectedAttacks.get(i).getEffect());
		}
		
		//Habilidades: lista de DTO vs lista entity. Recoorer por si hay 2 habilidades
		List<AbilityDTO> dtoAbilities = this.pokemonDTO_Test.getAbility();
		List<Ability> expectedAbilities = pokemonEntity.getAbility().stream().toList();
		assertEquals(dtoAbilities.size(), expectedAbilities.size());
				
		for (int i = 0; i < expectedAbilities.size(); i++) {			
			assertEquals(dtoAbilities.get(i).getInfo(), expectedAbilities.get(i).getInfo());
			assertEquals(dtoAbilities.get(i).getEffect(), expectedAbilities.get(i).getEffect());
		}
		
		//CardsRelated: de momento, todo null
		List<CardsRelatedDTO> dtoCardsRelated = this.pokemonDTO_Test.getCardsrelated();
		List<CardsRelated> expectedCardsRelated = pokemonEntity.getCardsrelated().stream().toList();
		assertEquals(dtoCardsRelated.size(), expectedCardsRelated.size());
		
		for (int i = 0; i < expectedCardsRelated.size(); i++) {
			assertEquals(dtoCardsRelated.get(i).getId(), expectedCardsRelated.get(i).getId());
		}
		
	}
	
	@Test
	void testMap_entityToPokemonDTO() {
		// Crear una instancia de Pokemon (entity) para la prueba
	    Pokemon pokemonEntity = new Pokemon();
	    pokemonEntity.setIdPokemon("A1-001");
	    pokemonEntity.setSetId("A1");
	    pokemonEntity.setIdNumber(1);
	    pokemonEntity.setPokemonName("Bulbasaur");
	    pokemonEntity.setCollectionPocket("Genetic Apex");
	    pokemonEntity.setDeck("A1M");
	    pokemonEntity.setCardType("Pokemon");
	    pokemonEntity.setCardColor("Grass");
	    pokemonEntity.setRarity(1);
	    pokemonEntity.setCardStage("Basic");
	    pokemonEntity.setRetreatCost(1);
	    pokemonEntity.setImageUrl("www.prueba.com");
	    pokemonEntity.setHpPokemon(70);
	    
	    //Inicializar los SETS para no tener el NULL Pointer
	    pokemonEntity.setAttack(new HashSet<>());
	    pokemonEntity.setAbility(new HashSet<>());
	    pokemonEntity.setCardsrelated(new HashSet<>());
	    
	    // Ataque
	    List<Attack> attacks = new ArrayList<>(pokemonEntity.getAttack());
	    Attack attack = new Attack();
	    attack.setId(1);
	    attack.setInfo("{GC} Vine Whip 40");
	    attack.setEffect(null);
	    attacks.add(attack);
	    pokemonEntity.setAttack(new HashSet<>(attacks));

	    // Habilidad
	    List<Ability> abilities = new ArrayList<>(pokemonEntity.getAbility());
	    pokemonEntity.setAbility(new HashSet<>(abilities));

	    // Cards relacionadas
	    List<CardsRelated> relatedCards = new ArrayList<>(pokemonEntity.getCardsrelated());
	    pokemonEntity.setCardsrelated(new HashSet<>(relatedCards));

	    // Convertir la entidad a DTO
	    PokemonDTO pokemonDTO = this.pokemonMapper.entityToPokemonDTO(pokemonEntity);

	    // Comparar los campos
	    assertEquals(pokemonEntity.getIdPokemon(), pokemonDTO.getIdPokemon());
	    assertEquals(pokemonEntity.getSetId(), pokemonDTO.getSetId());
	    assertEquals(pokemonEntity.getIdNumber(), pokemonDTO.getIdNumber());
	    assertEquals(pokemonEntity.getPokemonName(), pokemonDTO.getPokemonName());

	    // Comparar ataques
	    List<Attack> expectedAttacks = pokemonEntity.getAttack().stream().toList();
	    List<AttackDTO> dtoAttacks = pokemonDTO.getAttack();
	    assertEquals(expectedAttacks.size(), dtoAttacks.size());
	    for (int i = 0; i < expectedAttacks.size(); i++) {
	        assertEquals(expectedAttacks.get(i).getInfo(), dtoAttacks.get(i).getInfo());
	        assertEquals(expectedAttacks.get(i).getEffect(), dtoAttacks.get(i).getEffect());
	    }

	    // Comparar habilidades
	    List<Ability> expectedAbilities = pokemonEntity.getAbility().stream().toList();
	    List<AbilityDTO> dtoAbilities = pokemonDTO.getAbility();
	    assertEquals(expectedAbilities.size(), dtoAbilities.size());
	    for (int i = 0; i < expectedAbilities.size(); i++) {
	        assertEquals(expectedAbilities.get(i).getInfo(), dtoAbilities.get(i).getInfo());
	        assertEquals(expectedAbilities.get(i).getEffect(), dtoAbilities.get(i).getEffect());
	    }

	    // Comparar cartas relacionadas
	    List<CardsRelated> expectedCardsRelated = pokemonEntity.getCardsrelated().stream().toList();
	    List<CardsRelatedDTO> dtoCardsRelated = pokemonDTO.getCardsrelated();
	    assertEquals(expectedCardsRelated.size(), dtoCardsRelated.size());
	    for (int i = 0; i < expectedCardsRelated.size(); i++) {
	        assertEquals(expectedCardsRelated.get(i).getId(), dtoCardsRelated.get(i).getId());
	    }
	}
	
	@Test
	void findByIdPokemon_IncorrectId() {
		String id_Incorrect = "A1-287";
		assertThrows(EntityNotFoundException.class, () -> {
			this.pokemonService.findByIdPokemon(id_Incorrect);
		});		
	}
	
	@Test
	void findByIdPokemon() {		
		String id_Correct = "A1-284";
		PokemonDTO pokemonDTO = this.pokemonService.findByIdPokemon(id_Correct);		
		assertNotNull(pokemonDTO);
		assertEquals(pokemonDTO.getIdPokemon(), id_Correct);
		assertEquals(pokemonDTO.getPokemonName(), "Charizard ex");
		assertTrue(pokemonDTO.getHpPokemon() == 180);
		List<AttackDTO> attacks = pokemonDTO.getAttack();
		assertTrue(attacks.size() == 2); // 2 ataques		
	}
	
	@Test
	void findByPokemonName_IncorrectName() {
		String nameIncorrect = "Incorrect";
		// caso de Id incorrecto: excepción de entidad no encontrada
		assertThrows(EntityNotFoundException.class, () -> {
			this.pokemonService.findByPokemonName(nameIncorrect);
		});
		
	}
	@Test
	void findByPokemonName() {
		String nameCorrect = "Charizard";		
		List<PokemonDTO> pokemonsDTO = this.pokemonService.findByPokemonName(nameCorrect);		
		assertNotNull(pokemonsDTO);		
		assertTrue(pokemonsDTO.size() == 5);
	}
	
	@Test
	@Transactional
	void findByFiltersRarity() {
		
		//6 cartas de estrella
		Map<String, Object> filters = new HashMap<>();
		filters.put("rarity", 10);
		
		List<PokemonDTO> pokemons = this.pokemonService.findByFilters(filters);
		assertNotNull(pokemons);
		assertTrue(pokemons.size() == 6);
		 
	}
		
	@Test
	@Transactional
	void findByFiltersDeck_A1Deck() {
		Map<String, Object> filters_A1 = new HashMap<>();
		filters_A1.put("deck", "A1");		
		List<PokemonDTO> pokemon_A1Deck = this.pokemonService.findByFilters(filters_A1);
		assertNotNull(pokemon_A1Deck);
		assertTrue(pokemon_A1Deck.size() == 286);			
	}
	@Test
	@Transactional
	void findByFiltersDeck_A1aDeck() {
		Map<String, Object> filters_A1a = new HashMap<>();
		filters_A1a.put("deck", "A1a");		
		List<PokemonDTO> pokemon_A1aDeck = this.pokemonService.findByFilters(filters_A1a);
		assertNotNull(pokemon_A1aDeck);
		assertTrue(pokemon_A1aDeck.size() == 86);			
	}
	@Test
	@Transactional
	void findByFiltersDeck_PROMODeck() {
		Map<String, Object> filters_PROMO = new HashMap<>();
		filters_PROMO.put("deck", "PROMO");		
		List<PokemonDTO> pokemon_PROMODeck = this.pokemonService.findByFilters(filters_PROMO);
		assertNotNull(pokemon_PROMODeck);
		assertTrue(pokemon_PROMODeck.size() == 41);			
	}
	@Test
	@Transactional
	void findByFiltersDeck_A2Deck() {
		Map<String, Object> filters_A2 = new HashMap<>();
		filters_A2.put("deck", "A2");		
		List<PokemonDTO> pokemon_A2Deck = this.pokemonService.findByFilters(filters_A2);
		assertNotNull(pokemon_A2Deck);
		assertTrue(pokemon_A2Deck.size() == 207);			
	}
	
	

}
