package com.Backend.Service;

import com.Backend.dto.LoginUserDTO;
import com.Backend.dto.UserDTO;

public interface UserService {
	
	public UserDTO findByEmail(String email);
	public UserDTO registerUser(UserDTO userDTO);
	public UserDTO loginUser(LoginUserDTO loginUserDTO);

}
