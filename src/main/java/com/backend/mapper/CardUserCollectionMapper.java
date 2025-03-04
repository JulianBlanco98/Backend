package com.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.backend.model.CardUserCollection;
import com.backend.dto.CardUserCollectionDTO;

@Mapper(componentModel = "spring", uses = UserCardsMapper.class)
public interface CardUserCollectionMapper {
	
	CardUserCollectionMapper INSTANCE = Mappers.getMapper(CardUserCollectionMapper.class);
	
	@Mapping(source = "user.email", target = "userEmail")
	CardUserCollectionDTO toDTO(CardUserCollection cardUserCollection);
	
	@Mapping(source = "userEmail", target = "user.email")
	CardUserCollection toEntity(CardUserCollectionDTO cardUserCollectionDTO);
}
