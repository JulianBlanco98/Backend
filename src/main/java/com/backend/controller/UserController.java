package com.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.User;
import com.backend.service.JWTService;
import com.backend.service.UserService;
import com.backend.dto.LoginUserDTO;
import com.backend.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/pokemonTGC/users")
public class UserController {

	private final UserService userService;
	
	private final JWTService jwtService;

	@GetMapping("/{email}")
	public ResponseEntity<Map<String, Object>> getUserByEmail(@PathVariable final String email) {
		
		final UserDTO u = this.userService.findByEmail(email);

		return ResponseEntity.status(200).body(Map.of("message", "Usuario encontrado", "usuario", u));
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {

		final UserDTO u = this.userService.registerUser(userDTO);
		
		return ResponseEntity.status(201).body(Map.of("message", "Usuario registro con éxito", "usuario", u));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody final LoginUserDTO loginUserDTO) {
		
		final User u = this.userService.loginUser(loginUserDTO);
		final String jwtToken = this.jwtService.generateToken(u);
//		System.out.println("Token generado: "+ jwtToken);
		return ResponseEntity.status(200).body(Map.of("message", "Bienvenido " + u.getNickName() + " !", "token", jwtToken));

	}
}
