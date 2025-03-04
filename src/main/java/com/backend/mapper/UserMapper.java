package com.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.backend.model.User;
import com.backend.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	// User a User DTO: devolver del service despúes de una consulta a BD
	UserDTO entityToUserDTO(User user);
	
	// User DTO a User: entidad User para llamar a la BD usando el repository
	User userDTOToEntity(UserDTO userDTO);
	
	// User DTO a User sin el campo contraña --> Función Register --> Cifrar contra
	@Mapping(target = "password", ignore = true)
	User userDTOToEntityWithOutPassword(UserDTO userDTO);
	
}
