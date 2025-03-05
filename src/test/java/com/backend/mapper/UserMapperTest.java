package com.backend.mapper;

import com.backend.dto.UserDTO;
import com.backend.model.User;
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
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    private UserDTO userDTO;
    private User user;
    private UserDTO userDTOWithouthPassword;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setEmail("testUserDTO@test.com");
        userDTO.setUserName("userDTO");
        userDTO.setLastName("lastDTO");
        userDTO.setNickName("nickDTO");
        userDTO.setDateOfBirth(new Date());
        userDTO.setRol(User.Role.user);
        userDTO.setPassword("userDTOPassword");

        user = new User();
        user.setEmail("testUser@test.com");
        user.setUserName("user");
        user.setLastName("last");
        user.setNickName("nick");
        user.setDateOfBirth(new Date());
        user.setRol(User.Role.user);
        user.setPassword("userPassword");

        userDTOWithouthPassword = new UserDTO();
        userDTOWithouthPassword.setUserName("userDTOWithouthPasswordPass");
        userDTOWithouthPassword.setLastName("lastDTOPass");
        userDTOWithouthPassword.setNickName("nickDTOPass");
        userDTOWithouthPassword.setDateOfBirth(new Date());
        userDTOWithouthPassword.setRol(User.Role.user);


    }

    @AfterEach
    void tearDown() {
        user = null;
        userDTO = null;
        userDTOWithouthPassword = null;
    }

    @Test
    void userMapperInstanceIsNotNull() {
        assertNotNull(UserMapper.INSTANCE, "UserMapper INSTANCE debería estar inicializado");
    }

    @Test
    void entityToUserDTO_UserIsNull() {
        final User user1 = null;
        final UserDTO toDTO = this.userMapper.entityToUserDTO(user1);
        assertNull(toDTO);
    }

    @Test
    void entityToUserDTO() {
        final UserDTO toDTO = this.userMapper.entityToUserDTO(this.user);

        assertNotNull(toDTO);
        assertEquals(this.user.getEmail(), toDTO.getEmail(), "El email tiene que ser el mismo");
        assertEquals(this.user.getUserName(), toDTO.getUserName(), "El userName tiene que ser el mismo");
        assertEquals(this.user.getNickName(), toDTO.getNickName(), "El nickName tiene que ser el mismo");
        assertEquals(this.user.getLastName(), toDTO.getLastName(), "El lastName tiene que ser el mismo");
        assertEquals(this.user.getPassword(), toDTO.getPassword(), "La password tiene que ser la misma");

    }

    @Test
    void userDTOToEntity_UserDTOIsNull() {
        final UserDTO userDTO1 = null;
        final User toEntity = this.userMapper.userDTOToEntity(userDTO1);
        assertNull(toEntity);
    }

    @Test
    void userDTOToEntity() {
        final User toEntity = this.userMapper.userDTOToEntity(this.userDTO);
        assertNotNull(toEntity);
        assertEquals(this.userDTO.getEmail(), toEntity.getEmail(), "El email tiene que ser el mismo");
        assertEquals(this.userDTO.getUserName(), toEntity.getUserName(), "El userName tiene que ser el mismo");
        assertEquals(this.userDTO.getNickName(), toEntity.getNickName(), "El nickName tiene que ser el mismo");
        assertEquals(this.userDTO.getLastName(), toEntity.getLastName(), "El lastName tiene que ser el mismo");
        assertEquals(this.userDTO.getPassword(), toEntity.getPassword(), "La password tiene que ser la misma");
    }

    @Test
    void userDTOToEntityWithOutPassword_IsNull() {
        final UserDTO userDTO1 = null;
        final User user1 = this.userMapper.userDTOToEntityWithOutPassword(userDTO1);
        assertNull(user1);
    }

    @Test
    void userDTOToEntityWithOutPassword() {
        final User user1 = this.userMapper.userDTOToEntityWithOutPassword(userDTOWithouthPassword);

        assertNotNull(user1);
        assertNull(user1.getPassword(), "El campo de contra tiene que ser null");
    }
}