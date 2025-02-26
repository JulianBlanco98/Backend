package com.Backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.Pokemon;
import com.Backend.dto.PokemonCollectionDTO;
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
	
	// Para la colección de usuario, solo necesito idPokemon, imageUrl y pokemonName
	@Mapping(target = "ability", ignore = true)
	@Mapping(target = "attack", ignore = true)
	@Mapping(target = "hpPokemon", ignore = true)
	@Mapping(target = "setId", ignore = true)
	@Mapping(target = "idNumber", ignore = true)
	@Mapping(target = "cardColor", ignore = true)
	@Mapping(target = "cardStage", ignore = true)
	@Mapping(target = "cardsrelated", ignore = true)
	@Mapping(target = "cardType", ignore = true)
	@Mapping(target = "deck", ignore = true)
	@Mapping(target = "rarity", ignore = true)
	@Mapping(target = "retreatCost", ignore = true)
	@Mapping(target = "textCards", ignore = true)
	@Mapping(target = "collectionPocket", ignore = true)
	@Mapping(target = "weakness", ignore = true)
	Pokemon toEntityCollection(PokemonCollectionDTO pokeColeDTO);
	
	@Mapping(target = "idPokemon", source = "idPokemon")
	@Mapping(target = "pokemonName", source = "pokemonName")
	@Mapping(target = "imageUrl", source = "imageUrl")
	PokemonCollectionDTO toPokemonCollectionDTO(Pokemon pokemon);
	
	
}
