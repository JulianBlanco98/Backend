package com.backend.repository;

import com.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        String correctEmail = "test1@test.com";
        Optional<User> optionalUser = this.userRepository.findByEmail(correctEmail);
        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();
        assertNotNull(user);
        assertEquals(correctEmail, user.getEmail(), "El email tiene que ser el mismo");
    }
}