package com.backend.controller;

import com.backend.model.User;
import com.backend.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"/sql/clean_cardUserCollection_database.sql", "/sql/clean_pokemon_database.sql", "/sql/clean_user_database.sql", "/sql/user_data.sql", "/sql/pokemon_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTService jwtService;

    private String token;

    @BeforeEach
    void setUp() {

        // Simular un usuario de la BD como si estuviera logueado
        User user = new User();
        user.setEmail("test1@test.com");
        user.setUserName("user1");
        user.setRol(User.Role.user);
        this.token = "Bearer " + this.jwtService.generateToken(user);

    }

    @Test
    void findByIdPokemon() throws Exception {
        final String idPokemon = "A1-004";

        // Ejecutar la llamada HTTP --> Get
        this.mockMvc.perform(get("/pokemonTGC/pokemon/{idPokemon}", idPokemon)
                        .header("Authorization", token)) // Simular el token
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPokemon").value("A1-004"));
    }


    @Test
    void findPokemonByFilter() throws Exception {

        // Ejecutar la llamada HTTP --> Get
        this.mockMvc.perform(get("/pokemonTGC/pokemon/filtro")
                        .header("Authorization", token)
                        .param("cardColor", "Psychic")
                        .param("cardType", "Pokemon")
                        .param("cardStage", "Basic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCards").isNumber())
                .andExpect(jsonPath("$.totalCards").value(1))
                .andExpect(jsonPath("$.cards").isArray())
                .andExpect(jsonPath("$.cards[0].idPokemon").value("A1a-086"));

    }

}