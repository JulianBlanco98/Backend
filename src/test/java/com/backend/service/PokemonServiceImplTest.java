package com.backend.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonServiceImplTest {

    /*@BeforeEach
    void setUp() {
        // Iniciar aquí la tabla de pokemon como en el runner

    }

    @AfterEach
    void tearDown()  {

    }

    @Test
    void findAll() {
    }

    @Test
    void findByPokemonName() {
    }

    @Test
    void findByIdPokemon() {
    }

    @Test
    void findByFilters() {
    }

    @Test
    void findByIdPokemonCollection() {
    }*/
}