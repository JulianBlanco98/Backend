package com.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.UserCards;
import com.Backend.dto.CardUserCollectionDTO;
import com.Backend.dto.UserCardsDTO;

@Mapper(componentModel = "spring")
public interface CardUserCollectionMapper {
	
	CardUserCollectionMapper INSTANCE = Mappers.getMapper(CardUserCollectionMapper.class);
	
	@Mapping(source = "user.email", target = "userEmail")
	CardUserCollectionDTO toDTO(CardUserCollection cardUserCollection);
	
	@Mapping(source = "userEmail", target = "user.email")
	CardUserCollection toEntity(CardUserCollectionDTO cardUserCollectionDTO);
}
