package com.Backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Model.User;
import com.Backend.Repository.UserRepository;
import com.Backend.dto.UserDTO;
import com.Backend.mapper.UserMapper;


@SpringBootTest
class BackendApplication_UserTests {

	@Autowired
	private  UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Test
	void findByEmail_Correct() {
		
		//Recuperar un usuario con email correcto
		String emailCorrect = "prueba@gmail.com";
		
		Optional<User> userDTO = this.userRepository.findByEmail(emailCorrect);
		System.out.println("********* "+ userDTO.toString() + " *********");
		assertTrue(userDTO.isPresent()); // Existe el user
		assertEquals(emailCorrect, userDTO.get().getEmail()); // Es el mismo correo
		
	}
	@Test
	void findByEmail_Incorrect() {
		
		//Recuperar un usuario con email incorrecto
		String emailIncorrect = "pruebadasdsadasdsad@gmail.com";
		Optional<User> userDTO = this.userRepository.findByEmail(emailIncorrect);		
		assertFalse(userDTO.isPresent());
		
	}
	
	@Test
    void testMapUserToDTO() {
        // Verificar que el mapeo de User a UserDTO funciona correctamente
        User user = new User();
        user.setUserName("prueba test");
        user.setEmail("test@gmail.com");
        
        UserDTO userDTO = userMapper.entityToUserDTO(user);
        
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getUserName(), userDTO.getUserName());
    }
	
	@Test
	void testMapUserDTOToEntityWithoutPassword() {
		
		// El campo contraseña con este mapper no lo coge, ya que la contraseña se genera con Bcrypt en register
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName("prueba test");
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("1234");
        
        User user = userMapper.userDTOToEntityWithOutPassword(userDTO);
        
        assertNotEquals(userDTO.getPassword(), user.getPassword());
        assertNull(user.getPassword());
	}

}
