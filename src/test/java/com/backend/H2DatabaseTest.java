package com.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Profile("test")
@SpringBootTest()
@TestPropertySource("")
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void verifyUsersTableExists() {
        List<String> tables = jdbcTemplate.queryForList(
                "SHOW TABLES", String.class);
        System.out.println("Tablas en la BD H2: " + tables);
        assertTrue(tables.contains("users"));
    }

    @Test
    void verifyUsersHaveData() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
        System.out.println("Usuarios en H2: " + users);
        assertFalse(users.isEmpty(), "No se insertaron usuarios en H2");
    }

}
