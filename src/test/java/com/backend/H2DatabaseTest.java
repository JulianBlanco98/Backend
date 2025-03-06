package com.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Sql(scripts = {"/sql/clean_user_database.sql", "/sql/clean_pokemon_database.sql" ,"/sql/user_data.sql", "/sql/pokemon_data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void verifyUsersHaveData() {
        List<Map<String, Object>> users = this.jdbcTemplate.queryForList("SELECT * FROM users");
        System.out.println("Usuarios en H2: " + users);
        assertFalse(users.isEmpty(), "No se insertaron usuarios en H2");
    }

    @Test
    void verifyPokemonsHaveData() {
        List<Map<String, Object>> pokemons = this.jdbcTemplate.queryForList("SELECT * FROM pokemons");
        System.out.println("Pokemons en H2: " + pokemons);
        assertFalse(pokemons.isEmpty());
    }
}
