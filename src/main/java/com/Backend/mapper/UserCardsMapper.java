package com.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.UserCards;
import com.Backend.dto.UserCardsDTO;

@Mapper(componentModel = "spring")
public interface UserCardsMapper {

	UserCardsMapper INSTANCE = Mappers.getMapper(UserCardsMapper.class);
	
	@Mapping(source = "cardUserCollection.id", target = "user_collection_id")
	@Mapping(source = "pokemon.idPokemon", target = "cardId")
	UserCardsDTO toDTO(UserCards userCards);
	
	
	@Mapping(source = "user_collection_id", target = "cardUserCollection.id")
	@Mapping(source = "cardId", target = "pokemon.idPokemon")
	UserCards toEntity(UserCardsDTO userCardsDTO);
	
	
}
