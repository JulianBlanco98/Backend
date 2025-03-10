package com.backend.service;

import com.backend.dto.PokemonCollectionDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"/sql/clean_cardUserCollection_database.sql", "/sql/clean_pokemon_database.sql" , "/sql/clean_user_database.sql", "/sql/user_data.sql", "/sql/pokemon_data.sql", "/sql/cardUserCollection_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class CardUserCollectionServiceImplTest {

    @Autowired
    private CardUserCollectionService cardUserCollectionService;



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isCollectionInitialized_NotInitialized() {

        String email = "test2@test.com";
        String collection = "Smackdown";
        assertFalse(this.cardUserCollectionService.isCollectionInitialized(email, collection));
    }

    @Test
    void isCollectionInitialized() {
        String email = "test10@test.com";
        String collection = "Genetic";
        assertTrue(this.cardUserCollectionService.isCollectionInitialized(email, collection));
    }
    @Test
    void initializeCollection_IllegalCollection() {
        String email = "test10@test.com";
        String collection = "Illegal";
        assertThrows(IllegalArgumentException.class, () -> {
           this.cardUserCollectionService.initializeCollection(email, collection);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "test1@test.com, Genetic, 2",
            "test1@test.com, Mythical, 2",
            "test1@test.com, Smackdown, 1",
            "test1@test.com, PROMO, 4"
    })
    void initializeCollection(String email, String collection, int expectedCards) {
        int cardsInit = this.cardUserCollectionService.initializeCollection(email, collection);
        assertEquals(expectedCards, cardsInit, "Cantidad de cartas incoorecta para la expansion: " + collection);
    }

    /*
    @Test
    void initializeCollection_Genetic() {
        String email = "test1@test.com";
        String collection = "Genetic";
        int cardsInit = this.cardUserCollectionService.initializeCollection(email, collection);
        assertEquals(2, cardsInit, "Hay 2 cartas en la expansión de Genetic en la BD de pruebas");
    }

    @Test
    void initializeCollection_Mythical() {
        String email = "test1@test.com";
        String collection = "Mythical";
        int cardsInit = this.cardUserCollectionService.initializeCollection(email, collection);
        assertEquals(2, cardsInit, "Hay 2 cartas en la expansión de Mythical en la BD de pruebas");
    }

    @Test
    void initializeCollection_Smackdown() {
        String email = "test1@test.com";
        String collection = "Smackdown";
        int cardsInit = this.cardUserCollectionService.initializeCollection(email, collection);
        assertEquals(1, cardsInit, "Hay 2 cartas en la expansión de Smackdown en la BD de pruebas");
    }

    @Test
    void initializeCollection_Promo() {
        String email = "test1@test.com";
        String collection = "PROMO";
        int cardsInit = this.cardUserCollectionService.initializeCollection(email, collection);
        assertEquals(4, cardsInit, "Hay 2 cartas en la expansión de PROMO en la BD de pruebas");
    }*/

    @Test
    void getUserExpansionCards() {
        String email = "test10@test.com";
        String collection = "Genetic";
        List<PokemonCollectionDTO> expected = this.cardUserCollectionService.getUserExpansionCards(email, collection);
        assertNotNull(expected);
        assertEquals(2, expected.size(), "Hay 2 cartas en la expansión de Mythical en la BD de pruebas");
        assertEquals("A1-004", expected.get(0).getIdPokemon(), "El primer ID Pokemon es: A1-004");

    }

    @Test
    void updateUserCollection() {
    }
}