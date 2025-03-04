package com.backend.dto;

import java.util.Date;

import com.backend.model.User.Role;

import lombok.Data;

@Data
public class UserDTO {
	
	private String userName;
	private String lastName;
	private String nickName;
	private String email;
	private Date dateOfBirth;
	private String password;
	private Role rol;
	
}
