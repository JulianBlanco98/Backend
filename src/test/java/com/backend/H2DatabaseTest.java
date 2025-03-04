package com.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class H2DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void verifyUsersHaveData() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT * FROM users");
        System.out.println("Usuarios en H2: " + users);
        assertFalse(users.isEmpty(), "No se insertaron usuarios en H2");
    }

}
