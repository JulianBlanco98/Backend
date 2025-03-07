package com.backend.mapper;

import com.backend.dto.PokemonDTO;
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
import java.util.List;

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
        List<PokemonDTO.AttackDTO> attacks = new ArrayList<>();
        PokemonDTO.AttackDTO attack = new PokemonDTO.AttackDTO();
        attack.setId(1);
        attack.setInfo("{GC} Vine Whip 40");
        attack.setEffect(null);
        attacks.add(attack);
        pokemonDTO.setAttack(attacks);


    }

    @AfterEach
    void tearDown() {
        pokemonDTO = null;
        pokemon = null;
    }

    @Test
    void pokemonDTOTOEntity_DTOIsNull() {
        assertNull(this.pokemonMapper.pokemonDTOTOEntity(null));
    }

    @Test
    void pokemonDTOTOEntity() {
        final Pokemon toEntity = this.pokemonMapper.pokemonDTOTOEntity(this.pokemonDTO);
        assertNotNull(toEntity, "No debería ser nulo");
        assertEquals(this.pokemonDTO.getIdPokemon(), toEntity.getIdPokemon(), "El idPokemon tiene que ser el mismo: A1-001");
        assertEquals(this.pokemonDTO.getPokemonName(), toEntity.getPokemonName(), "El pokemonName tiene que ser el mismo: Bulbasaur");
        assertEquals(this.pokemonDTO.getDeck(), toEntity.getDeck(), "El deck tiene que ser el mismo: A1M");
        assertEquals(this.pokemonDTO.getRarity(), toEntity.getRarity(), "La rareza es 1");

        // Ver si el ataque se ha copiado bien: Recuperar ambos ataques: creado y recuperado
        List<PokemonDTO.AttackDTO> dtoAttacks = this.pokemonDTO.getAttack();
        List<Attack> entityAttacks = toEntity.getAttack().stream().toList();
        assertEquals(dtoAttacks.size(), entityAttacks.size(), "El tamaño de ambos tiene que ser el mismo");

        for (int i = 0; i < entityAttacks.size(); i++) {
            assertEquals(dtoAttacks.get(i).getInfo(), entityAttacks.get(i).getInfo(), "La info del ataque tiene que ser la misma");
            assertEquals(dtoAttacks.get(i).getEffect(), entityAttacks.get(i).getEffect(), "el effect del ataque tiene que ser el mismo");
        }


    }

    @Test
    void entityToPokemonDTO() {

    }


    @Test
    void toEntityCollection() {
    }

    @Test
    void toPokemonCollectionDTO() {
    }
}