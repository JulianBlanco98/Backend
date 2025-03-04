package com.backend.service;

import com.backend.model.User;
import com.backend.dto.LoginUserDTO;
import com.backend.dto.UserDTO;

public interface UserService {
	
	public UserDTO findByEmail(String email);
	public UserDTO registerUser(UserDTO userDTO);
	public User loginUser(LoginUserDTO loginUserDTO);

}
