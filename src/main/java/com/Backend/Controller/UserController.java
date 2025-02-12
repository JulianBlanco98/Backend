package com.Backend.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Model.User;
import com.Backend.Service.UserService;

@RestController
@RequestMapping("/pokemonTGC/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
		User u = userService.findByEmail(email);
		
		return ResponseEntity.status(200).body(Map.of(
					"message", "Usuario encontrado",
					"usuario", u));
		
			
		
	}
}
