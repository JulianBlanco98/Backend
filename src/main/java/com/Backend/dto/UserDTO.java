package com.Backend.dto;

import java.util.Date;

import com.Backend.Model.User.Role;

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
