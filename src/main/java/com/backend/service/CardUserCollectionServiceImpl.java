package com.backend.service;

import com.backend.model.CardUserCollection;
import com.backend.model.Pokemon;
import com.backend.model.User;
import com.backend.model.UserCards;
import com.backend.model.UserCards.CardCategory;
import com.backend.repository.CardUserCollectionRepository;
import com.backend.repository.PokemonRepository;
import com.backend.repository.UserCardsRepository;
import com.backend.repository.UserRepository;
import com.backend.dto.ListCardsUserCardsUpdateDTO;
import com.backend.dto.PokemonCollectionDTO;
import com.backend.dto.UserCardsDTO;
import com.backend.mapper.PokemonMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardUserCollectionServiceImpl implements CardUserCollectionService {

    private final CardUserCollectionRepository cardUserCollectionRepository;
    private final UserCardsRepository userCardsRepository;
    private final UserRepository userRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonMapper pokemonMapper;

    @Override
    public boolean isCollectionInitialized(String userEmail, String collectionSet) {

        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);

        return this.userCardsRepository.existsByCardUserCollectionAndCategory(cardUserCollection, getCategory(collectionSet));

    }


    @Override
    public int initializeCollection(String userEmail, String collectionSet) {

        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);

        // 4. Buscar por setId
        List<Pokemon> pokemonCollection = this.pokemonRepository.findBySetId(getSetId(collectionSet));

        // log.info("Tamaño de la expansion de Genetic: {} = 286", pokemonCollection.size());


        /*if (pokemonCollection.isEmpty()) {
            throw new EntityNotFoundException("No hay cartas disponibles para la colección " + collectionSet);
        }*/

        // Crear las userCards y asignarlas a la colección
        List<UserCards> userCards = pokemonCollection.stream().map(pokemon -> {
            UserCards card = new UserCards();
            card.setCardUserCollection(cardUserCollection);
            card.setPokemon(pokemon);
            card.setCategory(getCategory(collectionSet));
            card.setHasTheCard(false);
            return card;

        }).toList();

        this.userCardsRepository.saveAll(userCards);
        return userCards.size();
    }

    @Override
    public List<PokemonCollectionDTO> getUserExpansionCards(String userEmail, String collectionSet) {
        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);

        // Recuperar las usercards dependiendo del collectionSet
        List<UserCards> userCardsList = this.userCardsRepository.findByCardUserCollectionAndCategory(cardUserCollection, getCategory(collectionSet));
        /*if (userCardsList.isEmpty()) {
            throw new EntityNotFoundException("No hay cartas en la categoría " + collectionSet + " para este usuario.");
        }*/

        log.info("Se encontraron {} cartas en la expansión {}", userCardsList.size(), collectionSet);

        List<PokemonCollectionDTO> pokemonsDTO = userCardsList.stream()
                .map(userCard -> {
                    PokemonCollectionDTO dto = this.pokemonMapper.toPokemonCollectionDTO(userCard.getPokemon());
                    dto.setHasTheCard(userCard.isHasTheCard()); // añadir el boolean para el model del front
                    return dto;
                }).toList();


        return pokemonsDTO;

    }

    @Override
    public int updateUserCollection(String userEmail, String collectionSet, ListCardsUserCardsUpdateDTO updatedCards) {

        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);
        List<UserCards> userCardsList = this.userCardsRepository.findByCardUserCollectionAndCategory(cardUserCollection, getCategory(collectionSet));

        AtomicInteger update = new AtomicInteger(0);
        for (UserCardsDTO updatedCard : updatedCards.getCards()) {
            userCardsList.stream()
                    .filter(userCard -> userCard.getPokemon().getIdPokemon().equals(updatedCard.getCardId()))
                    .findFirst()
                    .ifPresent(userCard -> {
                        userCard.setHasTheCard(updatedCard.isHasTheCard());
                        update.getAndIncrement();
                    });
        }

        if(update.get() > 0) {
		    this.userCardsRepository.saveAll(userCardsList);

        }


        return update.get();
    }

    private User getUserByEmail(String userEmail) {
        return this.userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Email: " + userEmail + " no está registrado"));
    }

    private CardUserCollection getUserCollection(User user) {
        return cardUserCollectionRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("El usuario no tiene una colección inicializada."));
    }


    private String getSetId(String collectionSet) {
        return switch (collectionSet) {
            case "Genetic" -> "A1";
            case "Mythical" -> "A1a";
            case "Smackdown" -> "A2";
            case "PROMO" -> "PROMO";
            default -> throw new IllegalArgumentException("Colección inválida: " + collectionSet);
        };

    }

    private CardCategory getCategory(String collectionSet) {
        return CardCategory.valueOf(collectionSet);
    }


}


