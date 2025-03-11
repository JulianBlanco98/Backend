package com.backend.service;

import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.PokemonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"/sql/clean_cardUserCollection_database.sql", "/sql/clean_pokemon_database.sql", "/sql/pokemon_data.sql"})
@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonServiceImplTest {

    @Autowired
    private PokemonService pokemonService;

    @Transactional
    @Test
    void findAll() {
        final List<PokemonDTO> pokemon = this.pokemonService.findAll();
        assertNotNull(pokemon, "La lista no puede ser nula");
        assertFalse(pokemon.isEmpty(), "La lista no debería estar vacía");
        assertEquals("A1-004", pokemon.get(0).getIdPokemon(), "El primer pokemon tiene id: A1-004");
        assertEquals("A1-007", pokemon.get(1).getIdPokemon(), "El primer pokemon tiene id: A1-007");
    }


    @Test
    void findByPokemonName_IncorrectName() {
        final String nameIncorrect = "Incorrect";
        assertThrows(EntityNotFoundException.class, () -> {
            this.pokemonService.findByPokemonName(nameIncorrect);
        });

    }

    @Test
    void findByPokemonName() {
        final String correctName = "Venusaur ex";
        final List<PokemonDTO> correct = this.pokemonService.findByPokemonName(correctName);
        assertNotNull(correct, "Tiene que encontrar uno por el nombre de Venusaur Ex");
        assertEquals(1, correct.size(), "Hay solo un Venusaur Ex");
        assertEquals("Grass", correct.get(0).getCardColor(), "El tipo es Grass");

    }

    @Test
    void findByIdPokemon_IncorrectId() {
        final String idIncorrect = "A1-287";
        assertThrows(EntityNotFoundException.class, () -> {
            this.pokemonService.findByIdPokemon(idIncorrect);
        });
    }

    @Test
    void findByIdPokemon() {
        final String correctId = "A1-004";
        final PokemonDTO expected = this.pokemonService.findByIdPokemon(correctId);
        assertNotNull(expected, "Tiene que encontrar a Venusaur Ex");
        assertEquals("Venusaur ex", expected.getPokemonName(), "El nombre tiene que ser Venusaur Ex");
        assertEquals("Grass", expected.getCardColor(), "El tipo es Grass");

        final List<PokemonDTO.AttackDTO> attackDTOS = expected.getAttack();
        assertNotNull(attackDTOS);
        assertEquals(2, attackDTOS.size(), "Venusaur ex tiene 2 ataques");
        assertNull(attackDTOS.get(0).getEffect(), "Este ataque no tiene efecto. Deber ser NULL");
    }

    @Test
    void findByFilters_PokemonsNotFound() {
        Map<String, Object> filterPokemonName = new HashMap<>();
        filterPokemonName.put("pokemonName", "Charizard");
        assertThrows(EntityNotFoundException.class, () -> {
            this.pokemonService.findByFilters(filterPokemonName);
        });

    }

    @Transactional
    @Test
    void findByFilters_DeckToSetId() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("deck", "A1a");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Debería encontrar a 2 pokemons");
        assertEquals(2, expected.size(), "Hay 2 pokemon de este deck A1a");
        assertEquals("A1a-078", expected.get(0).getIdPokemon(), "IdPokemon esperado: A1a-078");
        assertEquals("A1a-086", expected.get(1).getIdPokemon(), "IdPokemon esperado: A1a-086");
    }

    @Transactional
    @Test
    void findByFilters_Deck() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("deck", "A1P");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Debe encontrar 1");
        assertEquals(1, expected.size(), "Encuentra un total de 1 pokemon");
        assertEquals("A1-007", expected.get(0).getIdPokemon(), "Id esperado: A1-007");
    }

    @Transactional
    @Test
    void findByFilters_PokemonName() {
        Map<String, Object> filterPokemonName = new HashMap<>();
        filterPokemonName.put("pokemonName", "Butterfree");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemonName);
        assertNotNull(expected, "Existe Butterfree");
        assertEquals("A1-007", expected.get(0).getIdPokemon(), "El idPokemon es: A1-007");
        assertEquals("Genetic Apex", expected.get(0).getCollectionPocket(), "la colección es: Genetic Apex");

    }

    @Transactional
    @Test
    void findByFilters_CardColorAndCardTypeAndCardStage() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("cardColor", "Psychic");
        filterPokemon.put("cardType", "Pokemon");
        filterPokemon.put("cardStage", "Basic");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Debería encontrar a Mew Ex");
        assertEquals("Mew ex", expected.get(0).getPokemonName(), "PokemonName: Mew ex");
    }

    @Transactional
    @Test
    void findByFilters_RarityAndWeaknessAndRetreatCost() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("rarity", 7);
        filterPokemon.put("weakness", "Lightning");
        filterPokemon.put("retreatCost", 1);

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Debería encontrar a Aerodactyl ex");
        assertEquals("Aerodactyl ex", expected.get(0).getPokemonName(), "PokemonName: Mew ex");
    }

    @Transactional
    @Test
    void findByFilters_SortByPokemonName() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("sortBy", "pokemonName");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Tienen que estar los 9 pokemons");
        assertEquals("A1a-078", expected.get(0).getIdPokemon(), "1: Aerodactyl");
        assertEquals("PROMO-029", expected.get(1).getIdPokemon(), "2: Blastoise");
        assertEquals("A1-007", expected.get(2).getIdPokemon(), "2: Butterfree");
        assertEquals("PROMO-019", expected.get(3).getIdPokemon(), "2: Greninja");
        assertEquals("PROMO-014", expected.get(4).getIdPokemon(), "2: Lapras Ex");
        assertEquals("A2-033", expected.get(5).getIdPokemon(), "4: Mamoswine");
        assertEquals("A1a-086", expected.get(6).getIdPokemon(), "3: Mew");
        assertEquals("PROMO-039", expected.get(7).getIdPokemon(), "4: Skarmory");
        assertEquals("A1-004", expected.get(8).getIdPokemon(), "4: Venusaur");

    }

    @Transactional
    @Test
    void findByFilters_SortByCardColor() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("sortBy", "cardColor");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);

        assertNotNull(expected, "Tienen que estar los 9 pokemons");
        assertEquals("A1a-078", expected.get(0).getIdPokemon(), "1: Fightning (Aerodactyl Ex)");
        assertEquals("A1-004", expected.get(1).getIdPokemon(), "2: Grass (Venusaaur Ex)");
        assertEquals("A1-007", expected.get(2).getIdPokemon(), "3: Grass (Butterfree)");
        assertEquals("PROMO-039", expected.get(3).getIdPokemon(), "4: Metal (Skarmory)");
        assertEquals("A1a-086", expected.get(4).getIdPokemon(), "5: Lightning (Mew Ex)");
        assertEquals("A2-033", expected.get(5).getIdPokemon(), "6: Water (Mamoswine)");
        assertEquals("PROMO-014", expected.get(6).getIdPokemon(), "7: Water (Lapras Ex)");
        assertEquals("PROMO-019", expected.get(7).getIdPokemon(), "8: Water (Greninja)");
        assertEquals("PROMO-029", expected.get(8).getIdPokemon(), "9: Water (Blastoise)");



    }

    @Transactional
    @Test
    void findByFilters_SortBySetId() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("sortBy", "setId");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);

        assertNotNull(expected, "Tienen que estar los 9 pokemons");
        assertEquals("A1-004", expected.get(0).getIdPokemon(), "1: A1-004 (Venusaur)");
        assertEquals("A1-007", expected.get(1).getIdPokemon(), "2: A1-007 (Butterfree)");
        assertEquals("A1a-078", expected.get(2).getIdPokemon(), "3: A1a-078 (Aerodactyl Ex)");
        assertEquals("A1a-086", expected.get(3).getIdPokemon(), "4: A1a-086 (Mew Ex)");
        assertEquals("A2-033", expected.get(4).getIdPokemon(), "5: A2-033 (Mamoswine)");
        assertEquals("PROMO-014", expected.get(5).getIdPokemon(), "6: PROMO-014 (Lapras Ex)");
        assertEquals("PROMO-019", expected.get(6).getIdPokemon(), "7: PROMO-019 (Greninja)");
        assertEquals("PROMO-029", expected.get(7).getIdPokemon(), "8: PROMO-029 (Blastoise)");
        assertEquals("PROMO-039", expected.get(8).getIdPokemon(), "9: PROMO-039 (Skarmory)");



    }

    @Transactional
    @Test
    void findByFilters_SortOrderASC() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("sortOrder", "ASC");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        assertNotNull(expected, "Tienen que estar los 9 pokemons");
        assertEquals("A1-004", expected.get(0).getIdPokemon(), "1 Id: A1-004 (Venusaur Ex)");
        assertEquals("A1-007", expected.get(1).getIdPokemon(), "2 Id: A1-007 (Butterfree)");
        assertEquals("PROMO-014", expected.get(2).getIdPokemon(), "3: PROMO-014 (Lapras Ex)");
        assertEquals("PROMO-019", expected.get(3).getIdPokemon(), "4: PROMO-019 (Greninja)");
        assertEquals("PROMO-029", expected.get(4).getIdPokemon(), "5: PROMO-029 (Blastoise)");
        assertEquals("A2-033", expected.get(5).getIdPokemon(), "6: A2-033 (Mamoswine)");
        assertEquals("PROMO-039", expected.get(6).getIdPokemon(), "7: PROMO-039 (Skarmory)");
        assertEquals("A1a-078", expected.get(7).getIdPokemon(), "8 Id: A1a-078 (Aerodactyl Ex)");
        assertEquals("A1a-086", expected.get(8).getIdPokemon(), "9 Id: A1a-086 (Mew Ex)");
    }

    @Transactional
    @Test
    void findByFilters_SortOrderDESC() {
        Map<String, Object> filterPokemon = new HashMap<>();
        filterPokemon.put("sortOrder", "DESC");

        final List<PokemonDTO> expected = this.pokemonService.findByFilters(filterPokemon);
        for (PokemonDTO pokemonDTO : expected) {
            System.out.println("Id pokemon orden: " + pokemonDTO.getIdPokemon());
        }

        /* Cambiar el Specification para que el orden sea descendiente */
        assertNotNull(expected, "Tienen que estar los 9 pokemons");
        assertEquals("A1a-086", expected.get(0).getIdPokemon(), "1 Id: A1a-086");
        assertEquals("A1a-078", expected.get(1).getIdPokemon(), "2 Id: A1a-078");
        assertEquals("PROMO-039", expected.get(2).getIdPokemon(), "3: PROMO-039 (Skarmory)");
        assertEquals("A2-033", expected.get(3).getIdPokemon(), "4: A2-033 (Mamoswine)");
        assertEquals("PROMO-029", expected.get(4).getIdPokemon(), "5: PROMO-029 (Blastoise)");
        assertEquals("PROMO-019", expected.get(5).getIdPokemon(), "6: PROMO-019 (Greninja)");
        assertEquals("PROMO-014", expected.get(6).getIdPokemon(), "7: PROMO-014 (Lapras Ex)");
        assertEquals("A1-007", expected.get(7).getIdPokemon(), "8 Id: A1-007");
        assertEquals("A1-004", expected.get(8).getIdPokemon(), "9 Id: A1-004");

    }


    @Test
    void findByIdPokemonCollection_IncorrectId() {
        final String idIncorrect = "S1-001";
        assertThrows(EntityNotFoundException.class, () -> {
           this.pokemonService.findByIdPokemonCollection(idIncorrect);
        });
    }

    @Test
    void findByIdPokemonCollection() {
        final String idCorrect = "A1-007";

        final PokemonCollectionDTO expected = this.pokemonService.findByIdPokemonCollection(idCorrect);
        assertNotNull(expected, "Tiene que encontrar uno");
        assertEquals(idCorrect, expected.getIdPokemon(), "El id es: A1-007");
    }
}