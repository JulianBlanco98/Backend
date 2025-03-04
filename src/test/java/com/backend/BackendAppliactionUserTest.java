package com.backend;

import com.backend.mapper.UserMapper;
import com.backend.model.User;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Profile("test")
class BackendAppliactionUserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserMapper userMapper;


    @Test
    void findByEmail_Correct() {
        System.out.println("Cantidad de usuarios en BD: " + userRepository.count()); // Verifica cuántos registros hay
        Optional<User> user = this.userRepository.findByEmail("test1@test.com");
        assertTrue(user.isPresent(), "El usuario test1 debería estar en la BD");
        assertEquals("test1@test.com", user.get().getEmail());

    }

}
