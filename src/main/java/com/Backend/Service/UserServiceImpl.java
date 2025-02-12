package com.Backend.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.Model.User;
import com.Backend.Repository.UserRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario con email " + email + " no encontrado"));
						
	}

}
