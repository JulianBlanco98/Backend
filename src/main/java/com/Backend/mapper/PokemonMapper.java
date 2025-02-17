package com.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.Pokemon;
import com.Backend.dto.PokemonDTO;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
	
	PokemonMapper INSTANCE = Mappers.getMapper(PokemonMapper.class);
	
	// Pokemon a PokemonDTO: devolver del Service
	@Mapping(target = "attack", source = "attack")
	@Mapping(target = "ability", source = "ability")
	@Mapping(target = "cardsrelated", source = "cardsrelated")
	PokemonDTO entityToPokemonDTO(Pokemon pokemon);
	
	// PokemonDTO a Pokemon: para llamar a la BD
	@Mapping(target = "attack", source = "attack")
    @Mapping(target = "ability", source = "ability")
	@Mapping(target = "cardsrelated", source = "cardsrelated")
	Pokemon pokemonDTOTOEntity(PokemonDTO pokemonDTO);
	
	
}
