package com.backend.service;

import com.backend.dto.LoginUserDTO;
import com.backend.dto.UserDTO;
import com.backend.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private UserDTO userDTO_test;
    private UserDTO userCorrectLoginDTO;

    @BeforeEach
    void setUp() {
        userDTO_test = new UserDTO();
        userDTO_test.setEmail("test5@test.com");
        userDTO_test.setUserName("user5");
        userDTO_test.setLastName("last5");
        userDTO_test.setNickName("nick5");
        userDTO_test.setDateOfBirth(new Date());
        userDTO_test.setRol(User.Role.user);
        userDTO_test.setPassword("hashedPassword5");

        userCorrectLoginDTO = new UserDTO();
        userCorrectLoginDTO.setEmail("test8@test.com");
        userCorrectLoginDTO.setUserName("user8");
        userCorrectLoginDTO.setLastName("last8");
        userCorrectLoginDTO.setNickName("nick8");
        userCorrectLoginDTO.setDateOfBirth(new Date());
        userCorrectLoginDTO.setRol(User.Role.user);
        userCorrectLoginDTO.setPassword("hashedPassword8");



    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findByEmail_NotFound() {
        final String wrongEmail = "noEmailTest@test.com";
        assertThrows(EntityNotFoundException.class, () -> {
            this.userService.findByEmail(wrongEmail);
        });

    }

    @Test
    void findByEmail() {
        final String correctEmail = "test1@test.com";
        UserDTO user = this.userService.findByEmail(correctEmail);
        assertEquals(correctEmail, user.getEmail());
        assertEquals("user1", user.getUserName());
        assertEquals("last1", user.getLastName());
        assertEquals("nick1", user.getNickName());
    }


    @Test
    void registerUser_EmailExist() {
        UserDTO incorrectUserDto = new UserDTO();
        incorrectUserDto.setEmail("test1@test.com");
        incorrectUserDto.setUserName("user5");
        incorrectUserDto.setLastName("last5");
        incorrectUserDto.setNickName("nick5");
        incorrectUserDto.setDateOfBirth(new Date());
        incorrectUserDto.setRol(User.Role.user);
        incorrectUserDto.setPassword("hashedPassword5");

        assertThrows(EntityExistsException.class, () -> {
            this.userService.registerUser(incorrectUserDto);
        });
    }

    @Test
    void registerUser_WithoutRole() {
        UserDTO userWithoutRole = new UserDTO();
        userWithoutRole.setEmail("test6@test.com");
        userWithoutRole.setUserName("user6");
        userWithoutRole.setLastName("last6");
        userWithoutRole.setNickName("nick6");
        userWithoutRole.setDateOfBirth(new Date());
        userWithoutRole.setPassword("hashedPassword6");
        userWithoutRole.setRol(null); // Aquí no se asigna ningún rol

        UserDTO registeredUser = this.userService.registerUser(userWithoutRole);

        assertNotNull(registeredUser);
        assertEquals("test6@test.com", registeredUser.getEmail());
        assertEquals(User.Role.user, registeredUser.getRol(), "El rol debe ser 'user' por defecto");
    }


    @Test
    void registerUser() {
        this.userService.registerUser(this.userDTO_test);

        final UserDTO userDTO = this.userService.findByEmail(this.userDTO_test.getEmail());
        assertNotNull(userDTO);
        assertEquals(this.userDTO_test.getEmail(), userDTO.getEmail(), "El email tiene que ser el mismo");
        assertEquals(this.userDTO_test.getUserName(), userDTO.getUserName(), "El userName tiene que ser el mismo");
        assertEquals(this.userDTO_test.getNickName(), userDTO.getNickName(), "El nickName tiene que ser el mismo");

    }

    @Test
    void loginUser_EmailNotFound() {
        LoginUserDTO incorrectEmail = new LoginUserDTO();
        incorrectEmail.setEmail("test8@test.com");
        incorrectEmail.setPassword("3123213");

        assertThrows(EntityNotFoundException.class, () -> {
           this.userService.loginUser(incorrectEmail);
        });

    }

    @Test
    void loginUser_PasswordIncorrect() {
        LoginUserDTO incorrectPassword = new LoginUserDTO();
        incorrectPassword.setEmail("test1@test.com");
        incorrectPassword.setPassword("hashedPassword2");

        assertThrows(IllegalArgumentException.class, () -> {
           this.userService.loginUser(incorrectPassword);
        });
    }

    @Test
    void loginUser() {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail("test8@test.com");
        loginUserDTO.setPassword("hashedPassword8");

        // Para el caso de login exitoso (contra cifrada)
        this.userService.registerUser(userCorrectLoginDTO);


        final User user = this.userService.loginUser(loginUserDTO);
        assertNotNull(user);
        assertEquals(this.userCorrectLoginDTO.getEmail(), user.getEmail(), "El email tiene que ser el mismo");
        assertEquals(this.userCorrectLoginDTO.getUserName(), user.getUserName(), "El userName tiene que ser el mismo");
        assertEquals(this.userCorrectLoginDTO.getNickName(), user.getNickName(), "El nickName tiene que ser el mismo");

    }


}