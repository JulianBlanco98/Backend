package com.Backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.Backend.Model.Ability;
import com.Backend.Model.Attack;
import com.Backend.Model.Pokemon;
import com.Backend.dto.PokemonDTO;
import com.Backend.dto.PokemonDTO.AbilityDTO;
import com.Backend.dto.PokemonDTO.AttackDTO;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
	
	PokemonMapper INSTANCE = Mappers.getMapper(PokemonMapper.class);
	
	// Pokemon a PokemonDTO: devolver del Service
	@Mapping(target = "attack", source = "attack")
	@Mapping(target = "ability", source = "ability")
	@Mapping(target = "cardsrelated", source = "cardsrelated")
	PokemonDTO entityToPokemonDTO(Pokemon pokemon);
	
	// PokemonDTO a Pokemon: para llamar a la BD
	@Mapping(target = "idPokemon", source = "idPokemon") 
	@Mapping(target = "attack", source = "attack")
    @Mapping(target = "ability", source = "ability")
	@Mapping(target = "cardsrelated", source = "cardsrelated")
	Pokemon pokemonDTOTOEntity(PokemonDTO pokemonDTO);
	
	// Mapping de Attack a AttackDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pokemons", ignore = true)
    List<AttackDTO> attacksToAttackDTOs(List<Attack> attacks);

    // Mapping de Ability a AbilityDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pokemons", ignore = true)
    List<AbilityDTO> abilitiesToAbilityDTOs(List<Ability> abilities);

    // Mapping de AttackDTO a Attack
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pokemons", ignore = true)
    List<Attack> attackDTOsToAttacks(List<PokemonDTO.AttackDTO> attackDTOs);

    // Mapping de AbilityDTO a Ability
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pokemons", ignore = true)
    List<Ability> abilityDTOsToAbilities(List<PokemonDTO.AbilityDTO> abilityDTOs);
	
}
