package com.backend.mapper;

import com.backend.dto.CardUserCollectionDTO;
import com.backend.model.CardUserCollection;
import com.backend.model.User;
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
class CardUserCollectionMapperTest {

    @Autowired
    private CardUserCollectionMapper cardUserCollectionMapper;

    private CardUserCollection entity;
    private CardUserCollectionDTO dto;

    @BeforeEach
    void setUp() {
        cardUserCollectionMapper = Mappers.getMapper(CardUserCollectionMapper.class);
    }

    @AfterEach
    void tearDown() {
        entity = null;
        dto = null;
    }

    @Test
    void cardUserCollectionMapperIsNotNull() {
        assertNotNull(CardUserCollectionMapper.INSTANCE, "Card User Collection Mapper debería estar inicializado");
    }

    @Test
    void toDTOIsNull() {
        assertNull(this.cardUserCollectionMapper.toDTO(null));
    }

    @Test
    void toDTO() {
        User user = new User();
        user.setEmail("test_user@test.com");

        entity = new CardUserCollection();
        entity.setUser(user);

        dto = this.cardUserCollectionMapper.toDTO(entity);
        assertNotNull(dto, "No puede ser nulo el DTO después de aplicar el mapper");
        assertEquals("test_user@test.com", dto.getUserEmail(), "El email tiene que ser el mismo");
    }

    @Test
    void toEntityIsNull() {
        assertNull(this.cardUserCollectionMapper.toEntity(null));
    }

    @Test
    void toEntity() {

        dto = new CardUserCollectionDTO();
        dto.setUserEmail("test_user@test.com");

        entity = this.cardUserCollectionMapper.toEntity(dto);
        assertNotNull(entity, "No puede ser nulo el entity después de aplicar el mapper");
        assertEquals("test_user@test.com", entity.getUser().getEmail(), "El email tiene que ser el mismo");
    }
}