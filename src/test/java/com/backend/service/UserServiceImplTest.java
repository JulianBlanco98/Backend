package com.backend.service;

import com.backend.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void findByEmail_NotFound() {
        String wrongEmail = "noEmailTest@test.com";
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.findByEmail(wrongEmail);
        });

    }

    @Test
    void findByEmail() {
        String correctEmail = "test1@test.com";
        UserDTO user = this.userService.findByEmail(correctEmail);
        assertEquals(correctEmail, user.getEmail());
        assertEquals("user1", user.getUserName());
        assertEquals("last1", user.getLastName());
        assertEquals("nick1", user.getNickName());
    }



    /*@Test
    void registerUser() {
    }

    @Test
    void loginUser() {
    }*/
}