package com.Backend.Service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.User;
import com.Backend.Repository.CardUserCollectionRepository;
import com.Backend.Repository.UserRepository;
import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;
import com.Backend.mapper.UserMapper;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final CardUserCollectionRepository cardUserCollectionRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder password;
	
	@Override
	public UserDTO findByEmail(final String email) {
		
//		return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario con email " + email + " no encontrado"));
		return this.userMapper.entityToUserDTO(this.userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario con email " + email + " no encontrado")));		
		
	}

	@Override
	public UserDTO registerUser(final UserDTO userDTO) {
		
		System.out.println("Entra en el service POST de registro");
		System.out.println(userDTO.toString());
		
		//isPresent() es porque es un Optional<> el método findByEmail()
		if(this.userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
			System.out.println("Entra en la excepcion de que existe el email");
			throw new EntityExistsException(userDTO.getEmail() + " ya está registrado");
		}
		
		User newUser = this.userMapper.userDTOToEntityWithOutPassword(userDTO);
		if(newUser.getRol() == null) {
			newUser.setRol(User.Role.user);
		}
		
		//Usar Bcrypt como en Node
		newUser.setPassword(this.password.encode(userDTO.getPassword())); 
		
		return this.userMapper.entityToUserDTO(this.userRepository.save(newUser));
	}

	@Override
	public User loginUser(LoginUserDTO loginUserDTO) {
		
		System.out.println("Entra en el service POST de login");
		System.out.println("--- "+ loginUserDTO + " ---");
		
		// Paso 1: Buscar por email
		Optional<User> optUser = userRepository.findByEmail(loginUserDTO.getEmail());
		
		// Paso 2: Verificar que existe el email
		if(!optUser.isPresent()) {
			throw new EntityNotFoundException("Email: " + loginUserDTO.getEmail() + " no está registrado");			
		}
				
		// Paso 3: Verificar que la contraseña introducida coincide con la del User en la BD
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		if(!b.matches(loginUserDTO.getPassword()	, optUser.get().getPassword())) {
			System.out.println("Contra incorrecta");
			throw new IllegalArgumentException("La contraseña introducida no es la correcta");
		}
		System.out.println("la contraseña coincide");
		
		// Paso 4(mas adelante): inicializar la tabla de CardCollection si no lo está
		
		Optional<CardUserCollection> optUserCollection = this.cardUserCollectionRepository.findByUser(optUser.get());
		
		// Si no está presente, creo una
		if(!optUserCollection.isPresent()) {
			
			CardUserCollection addCardUserCollection = new CardUserCollection();
			addCardUserCollection.setUser(optUser.get());
			
			this.cardUserCollectionRepository.save(addCardUserCollection);
		}
		
		
		return optUser.get();	
		
	}

}
