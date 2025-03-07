package com.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.backend.model.Pokemon;
import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.PokemonDTO;

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
	
	@Mapping(target = "idPokemon", source = "idPokemon")
	@Mapping(target = "pokemonName", source = "pokemonName")
	@Mapping(target = "imageUrl", source = "imageUrl")
	PokemonCollectionDTO toPokemonCollectionDTO(Pokemon pokemon);
	
	
}
