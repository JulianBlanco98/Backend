package com.Backend.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Backend.Model.User;
import com.Backend.Repository.UserRepository;
import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;
import com.Backend.mapper.UserMapper;
import com.Backend.security.UserDetailsImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	private final UserRepository userRepository;
	
	private final UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
	
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
		newUser.setPassword(this.passwordEncoder.encode(userDTO.getPassword())); 
		
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
		
		return optUser.get();	
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userMapper.userDTOToEntity(this.findByEmail(username));
		return new UserDetailsImpl(user);
	}

}
