package com.backend.service;

import com.backend.dto.*;
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
        assertTrue(expected.get(0).isHasTheCard(), "Tiene la primera carta de Genetic");
    }

    @Transactional
    @Test
    void updateUserCollection_NoUpdates() {
        String email = "test10@test.com";
        String collection = "Smackdown";
        ListCardsUserCardsUpdateDTO updatedCards = new ListCardsUserCardsUpdateDTO();
        List<UserCardsDTO> userCards = new ArrayList<>();
        UserCardsDTO userCard1 = new UserCardsDTO(); // mamoswine
        userCard1.setCardId("A2-033");
        userCard1.setHasTheCard(true);
        userCards.add(userCard1);
        updatedCards.setCards(userCards);

        int noUpdate = this.cardUserCollectionService.updateUserCollection(email, collection, updatedCards);
        assertEquals(0, noUpdate, "Es 0, ya que no se ha actualizado nada");
    }

    @Transactional
    @Test
    void updateUserCollection() {
        String email = "test2@test.com";
        String collection = "Mythical";
        ListCardsUserCardsUpdateDTO updatedCards = getListCardsUserCardsUpdateDTO();

        int update = this.cardUserCollectionService.updateUserCollection(email, collection, updatedCards);
        assertNotEquals(0, update, "Tiene que haberse actualizado correctamente");
        List<PokemonCollectionDTO> cards = this.cardUserCollectionService.getUserExpansionCards(email, collection);
        assertTrue(cards.get(0).isHasTheCard(), "la primera carta ahora tiene que ser true");
        assertTrue(cards.get(1).isHasTheCard(), "la segunda carta ahora tiene que ser true");
    }

    @Test
    void dashboardUserCollectionDTO() {
        String email = "test10@test.com";
        DashboardUserCollectionDTO response = this.cardUserCollectionService.getDashboardUserCollection(email);
        assertNotNull(response, "No puede ser nulo la respuesta");
        assertEquals(2, response.getCardsGenetic(), "2 cartas de Genetic");
        assertEquals(1, response.getCardsGeneticUser(), "1 carta de Genetic tiene el user");

        assertEquals(2, response.getCardsMythical(), "2 cartas de Mythical");
        assertEquals(1, response.getCardsMythicalUser(), "1 carta de Mythical tiene el user");

        assertEquals(1, response.getCardsSmackdown(), "1 carta de smackdown");
        assertEquals(1, response.getCardsSmackdownUser(), "1 carta de smackdown tiene el user");

        assertEquals(4, response.getCardsPROMO(), "4 cartas de PROMO");
        assertEquals(1, response.getCardsPROMOUser(), "1 carta de PROMO tiene el user");

        assertEquals(9, response.getTotalCards(), "Hay un total de 9 cartas en la BD de pokemon");
        assertEquals(4, response.getTotalCardsUser(), "El usuario tiene un total de 4 cartas");


    }

    @Test
    void getDeckUserCollection() {
        String email = "test10@test.com";
        String deck = "A2D";
        AccordionDTO dto = this.cardUserCollectionService.getDeckUserCollection(email, deck);
        assertNotNull(dto, "Accordion DTO no debería de ser null");
        assertEquals(1, dto.getCards().size(), "El usuario tiene una carta de este deck");
        assertEquals("A2-033", dto.getCards().get(0).getIdPokemon(), "IdPokemon tiene que ser A2-033");
        assertEquals("Mamoswine", dto.getCards().get(0).getPokemonName(), "Tiene que ser Mamoswine");
        assertTrue(dto.getCards().get(0).isHasTheCard(), "Este usuario tiene la carta");


    }

    private ListCardsUserCardsUpdateDTO getListCardsUserCardsUpdateDTO() {
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
        return updatedCards;
    }
}