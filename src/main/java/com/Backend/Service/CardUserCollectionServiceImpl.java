package com.Backend.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.Pokemon;
import com.Backend.Model.User;
import com.Backend.Model.UserCards;
import com.Backend.Model.UserCards.CardCategory;
import com.Backend.Repository.CardUserCollectionRepository;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Repository.UserCardsRepository;
import com.Backend.Repository.UserRepository;
import com.Backend.dto.ListCardsUserCardsUpdateDTO;
import com.Backend.dto.PokemonCollectionDTO;
import com.Backend.dto.UserCardsDTO;
import com.Backend.mapper.PokemonMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardUserCollectionServiceImpl implements CardUserCollectionService{

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
	public String initializeCollection(String userEmail, String collectionSet) {
		
		User user = this.getUserByEmail(userEmail);		
		CardUserCollection cardUserCollection = this.getUserCollection(user);
		
		// 4. Buscar por setId
		List<Pokemon> pokemonCollection = this.pokemonRepository.findBySetId(getSetId(collectionSet));
		
		log.info("Tamaño de la expansion de Genetic: {} = 286", pokemonCollection.size());
		
		
		if (pokemonCollection.isEmpty()) {
	        throw new EntityNotFoundException("No hay cartas disponibles para la colección " + collectionSet);
	    }
		
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
		
		return "La expansion " + collectionSet + " se ha inicializado correctamente";
	}
	
	@Override
	public List<PokemonCollectionDTO> getUserExpansionCards(String userEmail, String collectionSet) {
		// TODO Auto-generated method stub
		User user = this.getUserByEmail(userEmail);		
		CardUserCollection cardUserCollection = this.getUserCollection(user);
		
		// Recuperar las usercards dependiendo del collectionSet
		List<UserCards> userCardsList = this.userCardsRepository.findByCardUserCollectionAndCategory(cardUserCollection, getCategory(collectionSet));
		if (userCardsList.isEmpty()) {
	        throw new EntityNotFoundException("No hay cartas en la categoría " + collectionSet + " para este usuario.");
	    }
		
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
	public String updateUserCollection(String userEmail, String collectionSet, ListCardsUserCardsUpdateDTO updatedCards) {
		
		User user = this.getUserByEmail(userEmail);		
		CardUserCollection cardUserCollection = this.getUserCollection(user);
		List<UserCards> userCardsList = this.userCardsRepository.findByCardUserCollectionAndCategory(cardUserCollection, getCategory(collectionSet));
		if (userCardsList.isEmpty()) {
			throw new EntityNotFoundException("No hay cartas en la categoría " + collectionSet + " para este usuario.");
		}
		
		for(UserCardsDTO updatedCard: updatedCards.getCards()) {
			userCardsList.stream()
				.filter(userCard -> userCard.getPokemon().getIdPokemon().equals(updatedCard.getCardId()))
				.findFirst()
				.ifPresent(userCard -> userCard.setHasTheCard(updatedCard.isHasTheCard()));
		}
		
//		this.userCardsRepository.saveAll(userCardsList);
		
		try {
		    this.userCardsRepository.saveAll(userCardsList);
		} catch (Exception e) {
		    log.error("Error al guardar cartas: ", e);
		    throw e;
		}

		
		return "Coleccion guardada correctamente";
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
		String setId;
		switch (collectionSet) {
		case "Genetic":
			setId = "A1";
			break;
		case "Mythical":
			setId = "A1a";
			break;
		case "Smackdown":
			setId = "A2";
			break;
		case "PROMO":
			setId = "PROMO";
			break;
		default:
			throw new IllegalArgumentException("Colección inválida: " + collectionSet);
		}

		return setId;
	}
	
	private CardCategory getCategory(String collectionSet) {
		return CardCategory.valueOf(collectionSet);
	}



	



	
	

}


