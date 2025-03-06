package com.backend.service;

import com.backend.dto.PokemonDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"/sql/clean_pokemon_database.sql", "/sql/pokemon_data.sql"})
@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonServiceImplTest {

    @Autowired
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown()  {

    }


    @Transactional
    @Test
    void findAll() {
        List<PokemonDTO> pokemon = this.pokemonService.findAll();
        assertNotNull(pokemon, "La lista no puede ser nula");
        assertTrue(pokemon.size() > 0, "La lista no debería estar vacía");
        assertEquals("A1-004", pokemon.get(0).getIdPokemon(), "El primer pokemon tiene id: A1-004");
        assertEquals("A1-007", pokemon.get(1).getIdPokemon(), "El primer pokemon tiene id: A1-007");
    }


    @Test
    void findByPokemonName_IncorrectName() {
        String nameIncorrect = "Incorrect";
        assertThrows(EntityNotFoundException.class, () -> {
            this.pokemonService.findByPokemonName(nameIncorrect);
        });

    }

    @Test
    void findByPokemonName() {
        String correctName = "Venusaur ex";
        List<PokemonDTO> correct = this.pokemonService.findByPokemonName(correctName);
        assertNotNull(correct, "Tiene que encontrar uno por el nombre de Venusaur Ex");
        assertEquals(1, correct.size(), "Hay solo un Venusaur Ex");
        assertEquals("Grass", correct.get(0).getCardColor(), "El tipo es Grass");

    }

    @Test
    void findByIdPokemon_IncorrectId() {
        String idIncorrect = "A1-287";
        assertThrows(EntityNotFoundException.class, () -> {
            this.pokemonService.findByIdPokemon(idIncorrect);
        });
    }

    @Test
    void findByIdPokemon() {
        String correctId = "A1-004";
        PokemonDTO expected = this.pokemonService.findByIdPokemon(correctId);
        assertNotNull(expected, "Tiene que encontrar a Venusaur Ex");
        assertEquals("Venusaur ex", expected.getPokemonName(), "El nombre tiene que ser Venusaur Ex");
        assertEquals("Grass", expected.getCardColor(), "El tipo es Grass");
    }

    /*
    @Test
    void findByFilters() {
    }

    @Test
    void findByIdPokemonCollection() {
    }*/
}