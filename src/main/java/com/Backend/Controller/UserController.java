package com.Backend.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Model.User;
import com.Backend.Service.UserService;
import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;

@RestController
@RequestMapping("/pokemonTGC/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
		User u = userService.findByEmail(email);

		return ResponseEntity.status(200).body(Map.of("message", "Usuario encontrado", "usuario", u));
	}

	@PostMapping
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {

		User u = userService.registerUser(userDTO);
		
		return ResponseEntity.status(201).body(Map.of("message", "Usuario registro con éxito", "usuario", u));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
		
		User u = userService.loginUser(loginUserDTO);
		
		return null;
	}
}
