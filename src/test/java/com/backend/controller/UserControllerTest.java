package com.backend.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getUserByEmail() throws Exception{
        final String email = "test1@test.com";

        // Ejecutar la llamada HTTP
        this.mockMvc.perform(get("/pokemonTGC/users/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario encontrado"))
                .andExpect(jsonPath("$.usuario.email").value(email))
                .andExpect(jsonPath("$.usuario.userName").value("user1"))
                .andExpect(jsonPath("$.usuario.nickName").value("nick1"))
                .andExpect(jsonPath("$.usuario.rol").value("user"));



    }

    /*
    @Test
    void registerUser() {
    }


    @Test
    void loginUser() {
    }*/
}