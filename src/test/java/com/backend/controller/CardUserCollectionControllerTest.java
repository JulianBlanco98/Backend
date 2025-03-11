package com.backend.controller;

import com.backend.dto.ListCardsUserCardsUpdateDTO;
import com.backend.dto.UserCardsDTO;
import com.backend.model.User;
import com.backend.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"/sql/clean_cardUserCollection_database.sql", "/sql/clean_pokemon_database.sql", "/sql/clean_user_database.sql", "/sql/user_data.sql", "/sql/pokemon_data.sql", "/sql/cardUserCollection_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class CardUserCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    void setUp() {
        // Simular un usuario de la BD como si estuviera logueado
        User user = new User();
        user.setEmail("test2@test.com");
        user.setUserName("user2");
        user.setRol(User.Role.user);
        this.token = "Bearer " + this.jwtService.generateToken(user);
    }

    @Test
    void isCollectionInitialized() throws Exception {
        final String collectionSet = "Mythical";
        // Ejecutar la llamada HTTP --> Get
        this.mockMvc.perform(get("/pokemonTGC/collection/{collectionSet}/initialized", collectionSet)
                        .header("Authorization", token)) // Simular el token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.collectionInitialized").isBoolean())
                .andExpect(jsonPath("$.collectionInitialized").value(true));
    }

    @Test
    void initColection() throws Exception {
        final String collectionSet = "Genetic";
        // Ejecutar la llamada HTTP --> POST
        this.mockMvc.perform(post("/pokemonTGC/collection/{collectionSet}/initialize", collectionSet)
                        .header("Authorization", token)) // Simular el token
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.initialized").isBoolean())
                .andExpect(jsonPath("$.initialized").value(true))
                .andExpect(jsonPath("$.cardsCount").isNumber())
                .andExpect(jsonPath("$.cardsCount").value(2));

    }

    @Test
    void getUserExpansion() throws Exception{
        final String collectionSet = "Mythical";
        // Ejecutar la llamada HTTP --> Get
        this.mockMvc.perform(get("/pokemonTGC/collection/{collectionSet}/cards", collectionSet)
                        .header("Authorization", token)) // Simular el token
                .andExpect(status().isOk())
                .andExpect(jsonPath(".cards").isArray())
                .andExpect(jsonPath(".cards.length()").value(2));
    }

    @Test
    void updateUserExpansion() throws Exception{
        final String collectionSet = "Mythical";
        ListCardsUserCardsUpdateDTO updatedCards = getListCardsUserCardsUpdateDTO();


        // Ejecutar la llamada HTTP --> Put
        this.mockMvc.perform(put("/pokemonTGC/collection/{collectionSet}", collectionSet)
                .header("Authorization", token) // Simular el token
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedCards)))
                .andExpect(status().isOk());

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