package com.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Model.User;
import com.Backend.Model.User.Role;
import com.Backend.Repository.UserRepository;
import com.Backend.Service.UserService;
import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;
import com.Backend.mapper.UserMapper;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;


@SpringBootTest
class BackendApplication_UserTests {

	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserMapper userMapper;
	
	private UserDTO userDTOTest;
	
	@BeforeEach
	void setUp() {
		userDTOTest = new UserDTO();
		userDTOTest.setEmail("test@test.com");
		userDTOTest.setUserName("userName_test");
		userDTOTest.setLastName("lastName_test");
		userDTOTest.setNickName("nickName_test");
		userDTOTest.setDateOfBirth(new Date());
		userDTOTest.setRol(Role.user);
		userDTOTest.setPassword("1234");
		
		userService.registerUser(userDTOTest);
	}
	
	@AfterEach
	void tearDown() {
		this.userRepository.delete(this.userMapper.userDTOToEntity(this.userDTOTest));
	}
	
	@Test
	void findByEmail_Correct() {
			
		Optional<User> userDTO = this.userRepository.findByEmail(this.userDTOTest.getEmail());
		System.out.println("********* "+ userDTO.toString() + " *********");
		assertTrue(userDTO.isPresent()); // Existe el user
		assertEquals("test@test.com", userDTO.get().getEmail()); // Es el mismo correo
		
	}
	@Test
	void findByEmail_Incorrect() {
		
		//Recuperar un usuario con email incorrecto
		Optional<User> userDTO = this.userRepository.findByEmail("test2@test.com");		
		assertFalse(userDTO.isPresent());
		
	}
	
	@Test
	void registerUser_EmailExists() {
		assertThrows(EntityExistsException.class, () -> {
			this.userService.registerUser(this.userDTOTest);
		});
	}
	
	@Test
	void regiserUser() {
		
		Optional<User> userDTO = this.userRepository.findByEmail(this.userDTOTest.getEmail());
		assertTrue(userDTO.isPresent());
	}
	
	@Test
	void loginUser_EmailNotFound() {
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setEmail("test2@test.com");
		loginUserDTO.setPassword("1234");
		assertThrows(EntityNotFoundException.class, () -> {
			this.userService.loginUser(loginUserDTO);
		});
	}
	
	@Test
	void loginUser_PasswordIncorrect() {
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setEmail("test@test.com");
		loginUserDTO.setPassword("12345");
		assertThrows(IllegalArgumentException.class, () -> {
			this.userService.loginUser(loginUserDTO);
		});
	}
	
	@Test
	void loginUser_Success() {
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setEmail("test@test.com");
		loginUserDTO.setPassword("1234");
		
		final User user = this.userService.loginUser(loginUserDTO);
		assertEquals(user.getEmail(), this.userDTOTest.getEmail());
	}
	
	@Test
    void testMapUserToDTO() {
        // Verificar que el mapeo de User a UserDTO funciona correctamente
        User user = new User();
        user.setUserName("userName_test");
        user.setLastName("lastName_test");
        user.setNickName("nickName_test");
        user.setEmail("test@test.com");
        
        assertEquals(user.getEmail(), this.userDTOTest.getEmail());
        assertEquals(user.getUserName(), this.userDTOTest.getUserName());
        assertEquals(user.getLastName(), this.userDTOTest.getLastName());
        assertEquals(user.getNickName(), this.userDTOTest.getNickName());
    }
	
	@Test
	void testMapUserDTOToEntityWithoutPassword() {
		
		// El campo contraseña con este mapper no lo coge, ya que la contraseña se genera con Bcrypt en register
        final User user = userMapper.userDTOToEntityWithOutPassword(this.userDTOTest);
        
        assertNotEquals(this.userDTOTest.getPassword(), user.getPassword());
        assertNull(user.getPassword());
	}

}
