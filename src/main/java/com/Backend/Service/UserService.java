package com.Backend.Service;

import com.Backend.Model.User;

public interface UserService {
	
	public User findByEmail(String email);

}
