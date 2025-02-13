package com.Backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Backend.Exception.EmailExistException;
import com.Backend.Model.User;
import com.Backend.Repository.UserRepository;
import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario con email " + email + " no encontrado"));
						
	}

	@Override
	public User registerUser(UserDTO userDTO) {
		
		System.out.println("Entra en el service POST de registro");
		/*System.out.println("Nombre: " + userDTO.getUserName());
		System.out.println("Apellido: " + userDTO.getLastName());
		System.out.println("Nick: " + userDTO.getNickName());
		System.out.println("Email: " + userDTO.getEmail());
		System.out.println("Fecha de Nacimiento: " + userDTO.getDateOfBirth());
		System.out.println("Password: " + userDTO.getPassword());*/
		System.out.println(userDTO.toString());
		
		//isPresent() es porque es un Optional<> el método findByEmail()
		if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
			System.out.println("Entra en la excepcion de que existe el email");
			throw new EntityExistsException(userDTO.getEmail() + " ya está registrado");
		}
		
		User newUser = new User();
		newUser.setUserName(userDTO.getUserName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setNickName(userDTO.getNickName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setDateOfBirth(userDTO.getDateOfBirth());
		
		//Usar Bcrypt como en Node
		PasswordEncoder pass = new BCryptPasswordEncoder();	
		newUser.setPassword(pass.encode(userDTO.getPassword())); 
		
		return userRepository.save(newUser);
	}

	@Override
	public User loginUser(LoginUserDTO loginUserDTO) {
		
		System.out.println("Entra en el service POST de login");
		System.out.println("--- "+ loginUserDTO + " ---");
		
		
		
		return null;
	}

}
