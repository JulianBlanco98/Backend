package com.Backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Backend.Model.User;
import com.Backend.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findByEmail(String email) {
		
		User u = userRepository.findByEmail(email);
		if(u == null) {
			return null;
		}
		return u;				
	}

}
