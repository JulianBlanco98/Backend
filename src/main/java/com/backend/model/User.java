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

/**
 * Representa una entidad de usuario mapeada a la tabla "users" en la base de datos.
 * Esta clase utiliza anotaciones JPA para el mapeo ORM.
 * La anotación @Data de Lombok se usa para generar código repetitivo como getters, setters y métodos toString.
 * @author Julián Blanco González
 */
@Entity
@Table(name = "users")
@Data
public class User {

	/**
	 * Correo electrónico del usuario. Clave primaria, única y no nula.
	 */
	@Id
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	/**
	 * Nombre del usuario. Campo no nulo.
	 */
	@Column(name = "userName", nullable = false)
	private String userName;

	/**
	 * Apellidos del usuario. Campo no nulo.
	 */
	@Column(name = "lastName", nullable = false)
	private String lastName;

	/**
	 * Nick del usuario. Campo no nulo.
	 */
	@Column(name = "nickName", nullable = false)
	private String nickName;

	/**
	 * Fecha del usuario. Campo no nulo. Mapeado como tipo DATE.
	 */
	@Column(name = "dateOfBirth", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	/**
	 * Rol del usuario. Puede ser User o Admin. Campo no nulo. Enumerao como String.
	 */
	@Column(name = "rol", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role rol = Role.user;

	/**
	 * Contraseña del usuario. Campo no nulo.
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * Método público para darle el rol al usuario (user o admin)
	 */
	public enum Role {
		user, admin
	}
}
