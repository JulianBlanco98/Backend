package com.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "userName", nullable = false)
	private String userName;
	
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "nickName", nullable = false)
	private String nickName;
	
	@Column(name = "dateOfBirth", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(name = "rol", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role rol = Role.user;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	public enum Role {
		user, admin
	}
}
