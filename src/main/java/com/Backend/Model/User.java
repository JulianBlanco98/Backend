package com.Backend.Model;

import java.util.Date;

import javax.management.relation.Role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
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
	private Role rol = Role.USER;
	
	public enum Role {
		USER, ADMIN
	}
}
