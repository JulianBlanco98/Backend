package com.backend.controller;

import com.backend.dto.LoginUserDTO;
import com.backend.dto.UserDTO;
import com.backend.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"/sql/clean_user_database.sql", "/sql/user_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        this.jdbcTemplate.execute("DELETE FROM card_user_collection");
    }

    @Test
    void getUserByEmail() throws Exception{
        final String email = "test1@test.com";

        // Ejecutar la llamada HTTP Get
        this.mockMvc.perform(get("/pokemonTGC/users/{email}", email))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Usuario encontrado"))
                .andExpect(jsonPath("$.usuario.email").value(email))
                .andExpect(jsonPath("$.usuario.userName").value("user1"))
                .andExpect(jsonPath("$.usuario.nickName").value("nick1"))
                .andExpect(jsonPath("$.usuario.rol").value("user"));
    }


   @Test
    void registerUser() throws Exception{
        // Usuario de entrada
        UserDTO inputUserDTO = new UserDTO();
        inputUserDTO.setEmail("test9@test.com");
        inputUserDTO.setUserName("userDTO");
        inputUserDTO.setLastName("lastDTO");
        inputUserDTO.setNickName("nickDTO");
        inputUserDTO.setDateOfBirth(new Date());
        inputUserDTO.setRol(User.Role.user);
        inputUserDTO.setPassword("testprueba");

        // Ejecutar la llamada HTTP Post
        this.mockMvc.perform(post("/pokemonTGC/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputUserDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Usuario registro con éxito"))
                .andExpect(jsonPath("$.usuario.email").value("test9@test.com"))
                .andExpect(jsonPath("$.usuario.userName").value("userDTO"))
                .andExpect(jsonPath("$.usuario.nickName").value("nickDTO"))
                .andExpect(jsonPath("$.usuario.rol").value("user"));


    }

    @Test
    void loginUser() throws Exception{
        // prueba para ver si funciona el git en intelij
        LoginUserDTO inputLoginUserDTO = new LoginUserDTO();
        inputLoginUserDTO.setEmail("test10@test.com");
        inputLoginUserDTO.setPassword("testprueba");

        // Ejecutar la llamada POST
        this.mockMvc.perform(post("/pokemonTGC/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputLoginUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Bienvenido nick10 !"))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.token").isString());
    }
}