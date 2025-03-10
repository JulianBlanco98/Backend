package com.backend.service;

import com.backend.dto.ListCardsUserCardsUpdateDTO;
import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.UserCardsDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Test
    void getUserExpansionCards() {
        String email = "test10@test.com";
        String collection = "Genetic";
        List<PokemonCollectionDTO> expected = this.cardUserCollectionService.getUserExpansionCards(email, collection);
        assertNotNull(expected);
        assertEquals(2, expected.size(), "Hay 2 cartas en la expansión de Mythical en la BD de pruebas");
        assertEquals("A1-004", expected.get(0).getIdPokemon(), "El primer ID Pokemon es: A1-004");

    }

    @Transactional
    @Test
    void updateUserCollection() {
        String email = "test2@test.com";
        String collection = "Mythical";
        ListCardsUserCardsUpdateDTO updatedCards = new ListCardsUserCardsUpdateDTO();
        List<UserCardsDTO> userCards = new ArrayList<>();
        UserCardsDTO userCard1 = new UserCardsDTO(); // Aeodactyl Ex
        userCard1.setCardId("A1a-078");
        userCard1.setHasTheCard(true);
        UserCardsDTO userCard2 = new UserCardsDTO(); // Mew Ex
        userCard2.setCardId("A1a-086");
        userCard2.setHasTheCard(true);
        userCards.add(userCard1);
        userCards.add(userCard2);
        updatedCards.setCards(userCards);

        int update = this.cardUserCollectionService.updateUserCollection(email, collection, updatedCards);
        assertTrue(update != 0, "Tiene que haberse actualizado correctamente");

        List<PokemonCollectionDTO> cards = this.cardUserCollectionService.getUserExpansionCards(email, collection);
        assertTrue(cards.get(0).isHasTheCard(), "la primera carta ahora tiene que ser true");
        assertTrue(cards.get(1).isHasTheCard(), "la segunda carta ahora tiene que ser true");



    }
}