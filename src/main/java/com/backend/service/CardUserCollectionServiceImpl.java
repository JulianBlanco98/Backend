package com.backend.service;

import com.backend.dto.*;
import com.backend.model.CardUserCollection;
import com.backend.model.Pokemon;
import com.backend.model.User;
import com.backend.model.UserCards;
import com.backend.model.UserCards.CardCategory;
import com.backend.repository.CardUserCollectionRepository;
import com.backend.repository.PokemonRepository;
import com.backend.repository.UserCardsRepository;
import com.backend.repository.UserRepository;
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
        log.info("Se encontraron {} cartas en la expansión {}", userCardsList.size(), collectionSet);

        return userCardsList.stream()
                .map(userCard -> {
                    PokemonCollectionDTO dto = this.pokemonMapper.toPokemonCollectionDTO(userCard.getPokemon());
                    dto.setHasTheCard(userCard.isHasTheCard()); // añadir el boolean para el model del front
                    return dto;
                }).toList();


        // return pokemonsDTO;

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
                        if (userCard.isHasTheCard() != updatedCard.isHasTheCard()) { // Solo cuenta si cambia
                            userCard.setHasTheCard(updatedCard.isHasTheCard());
                            update.getAndIncrement();
                        }
                    });

        }

        if(update.get() > 0) {
		    this.userCardsRepository.saveAll(userCardsList);

        }


        return update.get();
    }

    @Override
    public DashboardUserCollectionDTO getDashboardUserCollection(String userEmail) {
        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);
        List<UserCards> userCards = this.userCardsRepository.findByCardUserCollection(cardUserCollection);

        long totalPokemonGenetic = this.pokemonRepository.countBySetId("A1");
        long totalPokemonMythtical = this.pokemonRepository.countBySetId("A1a");
        long totalPokemonPROMO = this.pokemonRepository.countBySetId("PROMO");
        long totalPokemonSmackdown = this.pokemonRepository.countBySetId("A2");

        long totalCards = totalPokemonGenetic + totalPokemonMythtical + totalPokemonPROMO + totalPokemonSmackdown;

        long totalPokemonGeneticUser = userCards.stream()
                .filter(card -> card.getCategory() == CardCategory.Genetic && card.isHasTheCard())
                .count();
        long totalPokemonMythicalUser = userCards.stream()
                .filter(card -> card.getCategory() == CardCategory.Mythical && card.isHasTheCard())
                .count();
        long totalPokemonPROMOUser = userCards.stream()
                .filter(card -> card.getCategory() == CardCategory.PROMO && card.isHasTheCard())
                .count();
        long totalPokemonSmackdownUser = userCards.stream()
                .filter(card -> card.getCategory() == CardCategory.Smackdown && card.isHasTheCard())
                .count();

        long totalCardsUser = totalPokemonGeneticUser + totalPokemonMythicalUser + totalPokemonPROMOUser + totalPokemonSmackdownUser;

        DashboardUserCollectionDTO response = new DashboardUserCollectionDTO();
        response.setCardsGenetic((int) totalPokemonGenetic);
        response.setCardsMythical((int) totalPokemonMythtical);
        response.setCardsPROMO((int) totalPokemonPROMO);
        response.setCardsSmackdown((int) totalPokemonSmackdown);

        response.setCardsGeneticUser((int) totalPokemonGeneticUser);
        response.setCardsMythicalUser((int) totalPokemonMythicalUser);
        response.setCardsPROMOUser((int) totalPokemonPROMOUser);
        response.setCardsSmackdownUser((int) totalPokemonSmackdownUser);

        response.setTotalCards((int) totalCards);
        response.setTotalCardsUser((int) totalCardsUser);

        return response;

    }

    @Override
    public AccordionDTO getDeckUserCollection(String userEmail, String deck) {
        User user = this.getUserByEmail(userEmail);
        CardUserCollection cardUserCollection = this.getUserCollection(user);
        List<UserCards> userCards = this.userCardsRepository.findByCardUserCollectionAndPokemon_Deck(cardUserCollection, deck);
        log.info("Deck: {}", deck);
        System.out.println(userCards);




        AccordionDTO response = new AccordionDTO();
        response.setDeckCards(userCards.size());



        return response;
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


