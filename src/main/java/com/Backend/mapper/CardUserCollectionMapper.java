package com.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.CardUserCollectionDetail;
import com.Backend.dto.CardUserCollectionDTO;
import com.Backend.dto.CardUserCollectionDeatilsDTO;

@Mapper(componentModel = "spring")
public interface CardUserCollectionMapper {
	
	CardUserCollectionMapper INSTANCE = Mappers.getMapper(CardUserCollectionMapper.class);
	
	@Mapping(source = "user.email", target = "userEmail")
	CardUserCollectionDTO entityToCollectionDTO(CardUserCollection cardUserCollection);
		
	@Mapping(source = "userEmail", target = "user.email")
	CardUserCollection toEntity(CardUserCollectionDTO cardUserCollectionDTO);
	
	@Mapping(source = "cardUserCollection.id", target = "collectionId")
    @Mapping(source = "pokemon.idPokemon", target = "pokemonId")
    CardUserCollectionDeatilsDTO toDetailDTO(CardUserCollectionDetail cardUserCollectionDetail);

    @Mapping(source = "collectionId", target = "cardUserCollection.id")
    @Mapping(source = "pokemonId", target = "pokemon.idPokemon")
    CardUserCollectionDetail toDetailEntity(CardUserCollectionDeatilsDTO cardUserCollectionDeatilsDTO);
}
