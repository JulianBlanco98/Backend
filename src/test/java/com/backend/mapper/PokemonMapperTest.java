package com.backend.mapper;

import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.PokemonDTO;
import com.backend.model.Ability;
import com.backend.model.Attack;
import com.backend.model.Pokemon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PokemonMapperTest {

    @Autowired
    private PokemonMapper pokemonMapper;

    private PokemonDTO pokemonDTO;
    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        
        // Crear PokemonDTO de prueba
        pokemonDTO = new PokemonDTO();
        pokemonDTO.setIdPokemon("A1-001");
        pokemonDTO.setSetId("A1");
        pokemonDTO.setIdNumber(1);
        pokemonDTO.setPokemonName("Bulbasaur");
        pokemonDTO.setCollectionPocket("Genetic Apex");
        pokemonDTO.setDeck("A1M");
        pokemonDTO.setCardType("Pokemom");
        pokemonDTO.setCardColor("Grass");
        pokemonDTO.setRarity(1);
        pokemonDTO.setCardStage("Basic");
        pokemonDTO.setRetreatCost(1);
        pokemonDTO.setImageUrl("www.prueba.com");
        pokemonDTO.setCardsrelated(null);
        pokemonDTO.setHpPokemon(70);

        // Ataque del PokemonDTO
        List<PokemonDTO.AttackDTO> attacksDTO = new ArrayList<>();
        PokemonDTO.AttackDTO attackDTO = new PokemonDTO.AttackDTO();
        attackDTO.setId(1);
        attackDTO.setInfo("{GC} Vine Whip 40");
        attackDTO.setEffect(null);
        attacksDTO.add(attackDTO);
        pokemonDTO.setAttack(attacksDTO);

        // Ability del Pokemon DTO
        List<PokemonDTO.AbilityDTO> abilitysDTO = new ArrayList<>();
        PokemonDTO.AbilityDTO abilityDTO = new PokemonDTO.AbilityDTO();
        abilityDTO.setId(1);
        abilityDTO.setInfo("Powder Heal");
        abilityDTO.setEffect("Once during your turn, you may heal 20 damage from each of your Pokémon.");
        abilitysDTO.add(abilityDTO);
        pokemonDTO.setAbility(abilitysDTO);

        // Crear Entity Pokemon de Prueba
        pokemon = new Pokemon();
        pokemon.setIdPokemon("A1-001");
        pokemon.setSetId("A1");
        pokemon.setIdNumber(1);
        pokemon.setPokemonName("Bulbasaur");
        pokemon.setCollectionPocket("Genetic Apex");
        pokemon.setDeck("A1M");
        pokemon.setCardType("Pokemom");
        pokemon.setCardColor("Grass");
        pokemon.setRarity(1);
        pokemon.setCardStage("Basic");
        pokemon.setRetreatCost(1);
        pokemon.setImageUrl("www.prueba.com");
        pokemon.setCardsrelated(null);
        pokemon.setHpPokemon(70);

        // Ataque del Entity Pokemon (Set)
        Set<Attack> attacks = new HashSet<>();
        Attack attack1 = new Attack();
        attack1.setId(1);
        attack1.setInfo("{GC} Vine Whip 40");
        attack1.setEffect(null);
        attacks.add(attack1);
        pokemon.setAttack(attacks);

        // Ability del Entity Pokemon (Set)
        Set<Ability> abilities = new HashSet<>();
        Ability ability1 = new Ability();
        ability1.setId(1);
        ability1.setInfo("Powder Heal");
        ability1.setEffect("Once during your turn, you may heal 20 damage from each of your Pokémon.");
        abilities.add(ability1);
        pokemon.setAbility(abilities);


    }

    @AfterEach
    void tearDown() {
        pokemonDTO = null;
        pokemon = null;
    }

    @Test
    void pokemonMapperInstanceIsNotNull() {
        assertNotNull(PokemonMapper.INSTANCE, "Pokemon Mapper debería estar inicializado");
    }

    @Test
    void pokemonDTOTOEntity_DTOIsNull() {
        assertNull(this.pokemonMapper.pokemonDTOTOEntity(null));
    }

    @Test
    void pokemonDTOTOEntity_AttackDTOAndAbilityDTOIsNull() {
        PokemonDTO pokemonDTO1 = this.pokemonDTO;
        pokemonDTO1.setAttack(null);
        pokemonDTO1.setAbility(null);
        pokemonDTO1.setCardsrelated(null);

        Pokemon toEntityNull = this.pokemonMapper.pokemonDTOTOEntity(pokemonDTO1);

        assertNull(toEntityNull.getAttack(), "Ataque nulo");
        assertNull(toEntityNull.getAbility(), "Habilidad nula");
        assertNull(toEntityNull.getCardsrelated(), "Cards Related Nula");
    }

    @Test
    void pokemonDTOTOEntity() {
        final Pokemon toEntity = this.pokemonMapper.pokemonDTOTOEntity(this.pokemonDTO);
        assertNotNull(toEntity, "No debería ser nulo");
        assertEquals(this.pokemonDTO.getIdPokemon(), toEntity.getIdPokemon(), "El idPokemon tiene que ser el mismo: A1-001");
        assertEquals(this.pokemonDTO.getPokemonName(), toEntity.getPokemonName(), "El pokemonName tiene que ser el mismo: Bulbasaur");
        assertEquals(this.pokemonDTO.getDeck(), toEntity.getDeck(), "El deck tiene que ser el mismo: A1M");
        assertEquals(this.pokemonDTO.getRarity(), toEntity.getRarity(), "La rareza es 1");

        // Ver si el ataque y la habilidad se ha copiado bien: Recuperar ambos ataques: creado y recuperado
        List<PokemonDTO.AttackDTO> dtoAttacks = this.pokemonDTO.getAttack();
        List<Attack> entityAttacks = toEntity.getAttack().stream().toList();
        List<PokemonDTO.AbilityDTO> dtoAbilitys = this.pokemonDTO.getAbility();
        List<Ability> entityAbilitys = toEntity.getAbility().stream().toList();
        assertEquals(dtoAttacks.size(), entityAttacks.size(), "El tamaño de ambos tiene que ser el mismo");
        assertEquals(dtoAbilitys.size(), entityAbilitys.size(), "El tamaño de ambos tiene que ser el mismo");

        for (int i = 0; i < entityAttacks.size(); i++) {
            assertEquals(dtoAttacks.get(i).getInfo(), entityAttacks.get(i).getInfo(), "La info del ataque tiene que ser la misma");
            assertEquals(dtoAttacks.get(i).getEffect(), entityAttacks.get(i).getEffect(), "El effect del ataque tiene que ser el mismo");
            assertEquals(dtoAbilitys.get(i).getInfo(), entityAbilitys.get(i).getInfo(), "La info de la habilidad tiene que ser el mismo");
            assertEquals(dtoAbilitys.get(i).getEffect(), entityAbilitys.get(i).getEffect(), "El effect de la habilidad tiene que ser el mismo");

        }
    }

    @Test
    void entityToPokemonDTO_EntityIsNull() {
        assertNull(this.pokemonMapper.entityToPokemonDTO(null));
    }

    @Test
    void entityToPokemonDTO_AttackAndAbilityNull() {
        Pokemon pokemon1 = this.pokemon;
        pokemon1.setAttack(null);
        pokemon1.setAbility(null);
        pokemon1.setCardsrelated(null);

        PokemonDTO toDTONull = this.pokemonMapper.entityToPokemonDTO(pokemon1);
        assertNull(toDTONull.getAttack(), "Ataque nulo");
        assertNull(toDTONull.getAbility(), "Habilidad nula");
        assertNull(toDTONull.getCardsrelated(), "Cards Related Nula");
    }

    @Test
    void entityToPokemonDTO() {

        final PokemonDTO toDTO = this.pokemonMapper.entityToPokemonDTO(this.pokemon);
        assertNotNull(toDTO);
        assertEquals(this.pokemon.getIdPokemon(), toDTO.getIdPokemon(), "El idPokemon tiene que ser el mismo: A1-001");
        assertEquals(this.pokemon.getPokemonName(), toDTO.getPokemonName(), "El pokemonName tiene que ser el mismo: Bulbasaur");
        assertEquals(this.pokemon.getDeck(), toDTO.getDeck(), "El deck tiene que ser el mismo: A1M");
        assertEquals(this.pokemon.getRarity(), toDTO.getRarity(), "La rareza es 1");

        List<Attack> entityAttacks = this.pokemon.getAttack().stream().toList();
        List<PokemonDTO.AttackDTO> dtoAttacks = toDTO.getAttack();
        List<Ability> entityAbilities = this.pokemon.getAbility().stream().toList();
        List<PokemonDTO.AbilityDTO> dtoAbilities = toDTO.getAbility();
        assertEquals(entityAttacks.size(), dtoAttacks.size(), "El tamaño de ambos tiene que ser el mismo");
        assertEquals(entityAbilities.size(), dtoAbilities.size(), "El tamaño de ambos tiene que ser el mismo");

        for (int i = 0; i < dtoAttacks.size(); i++) {
            assertEquals(entityAttacks.get(i).getInfo(), dtoAttacks.get(i).getInfo(), "La info del ataque tiene que ser la misma");
            assertEquals(entityAttacks.get(i).getEffect(), dtoAttacks.get(i).getEffect(), "El effect del ataque tiene que ser el mismo");
            assertEquals(entityAbilities.get(i).getInfo(), dtoAbilities.get(i).getInfo(), "La info de la habilidad tiene que ser el mismo");
            assertEquals(entityAbilities.get(i).getEffect(), dtoAbilities.get(i).getEffect(), "El effect de la habilidad tiene que ser el mismo");

        }


    }

    @Test
    void toPokemonCollectionDTO_IsNull() {
        assertNull(this.pokemonMapper.toPokemonCollectionDTO(null));
    }

    @Test
    void toPokemonCollectionDTO() {
        final PokemonCollectionDTO pokemonCollectionDTO = this.pokemonMapper.toPokemonCollectionDTO(this.pokemon);
        assertNotNull(pokemonCollectionDTO);
        assertEquals(this.pokemon.getIdPokemon(), pokemonCollectionDTO.getIdPokemon(), "El idPokemon tiene que ser el mismo: A1-001");
        assertEquals(this.pokemon.getPokemonName(), pokemonCollectionDTO.getPokemonName(), "El pokemonName tiene que ser Bulbasaur");
        assertEquals(this.pokemon.getImageUrl(), pokemonCollectionDTO.getImageUrl(), "La URL tiene que ser la misma");

    }
}