package com.backend.mapper;

import com.backend.dto.UserCardsDTO;
import com.backend.model.CardUserCollection;
import com.backend.model.Pokemon;
import com.backend.model.UserCards;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class UserCardsMapperTest {

    @Autowired
    private UserCardsMapper userCardsMapper;

    private UserCards entity;
    private UserCardsDTO dto;


    @BeforeEach
    void setUp() {
        userCardsMapper = Mappers.getMapper(UserCardsMapper.class);

    }

    @AfterEach
    void tearDown() {
        this.entity = null;
        this.dto = null;
    }

    @Test
    void userCardsMapperIsNotNull() {
        assertNotNull(UserCardsMapper.INSTANCE, " UserCards Mapper debería estar inicializado");
    }

    @Test
    void toDTOIsNull() {
        assertNull(this.userCardsMapper.toDTO(null));
    }

    @Test
    void toDTO() {
        Pokemon pokemon1;
        pokemon1 = new Pokemon();
        pokemon1.setIdPokemon("A1-004");
        pokemon1.setPokemonName("Venusaur Ex");
        CardUserCollection cardUserCollection;
        cardUserCollection = new CardUserCollection();
        cardUserCollection.setId(1L);

        this.entity = new UserCards();
        this.entity.setId(1L);
        this.entity.setCardUserCollection(cardUserCollection);
        this.entity.setPokemon(pokemon1);
        this.entity.setCategory(UserCards.CardCategory.Genetic);
        this.entity.setHasTheCard(false);

        this.dto = this.userCardsMapper.toDTO(this.entity);
        assertNotNull(this.dto, "No debería ser nulo el DTO");
        assertEquals(1L, this.dto.getId(), "Deben tener el mismo Id");
        assertEquals("A1-004", this.dto.getCardId(), "El IdPokemon tiene que ser A1-004");
        assertFalse(this.dto.isHasTheCard(), "Tiene que ser false porque la carta no la tiene el usuario");
    }

    @Test
    void toEntityIsNull() {
        assertNull(this.userCardsMapper.toEntity(null));
    }

    @Test
    void toEntity() {
        this.dto = new UserCardsDTO();
        this.dto.setId(1L);
        this.dto.setUser_collection_id(1L);
        this.dto.setCardId("A1-004");
        this.dto.setCategory(UserCards.CardCategory.Genetic);
        this.dto.setHasTheCard(true);

        this.entity = this.userCardsMapper.toEntity(this.dto);
        assertNotNull(this.entity, "No puede ser nulo entity");
        assertEquals(1L, this.entity.getId(), "El Id es 1L");
        assertEquals(1L, this.entity.getCardUserCollection().getId());
        assertEquals("A1-004", this.entity.getPokemon().getIdPokemon(), "El IdPokemon tiene que ser A1-004");
        assertTrue(this.entity.isHasTheCard(), "El usuario tiene esta carta");
    }
}