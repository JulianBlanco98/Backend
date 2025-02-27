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
		
		// 1. Buscar usuario por email
		Optional<User> optUser = this.userRepository.findByEmail(userEmail);
		
		// 2: Verificar que existe un usuario con ese email
		if (!optUser.isPresent()) {
			throw new EntityNotFoundException("Email: " + userEmail + " no está registrado");
		}
		
		// 3. Buscar la colección del usuario
		Optional<CardUserCollection> optUserCollection = this.cardUserCollectionRepository.findByUser(optUser.get());
		
		// 4. Verificar que existe la colección del usuario
		if(optUserCollection.isEmpty()) {
			return false;
		}
		
		// 5. Verificar si la expansión pasada por parámetro está inicializada
		
		return this.userCardsRepository.existsByCardUserCollectionAndCategory(optUserCollection.get(), getCategory(collectionSet));
		
	}



	@Override
	public String initializeCollection(String userEmail, String collectionSet) {
		
		// 1. Buscar usuario por email
		Optional<User> optUser = this.userRepository.findByEmail(userEmail);

		// 2: Verificar que existe un usuario con ese email
		if (!optUser.isPresent()) {
			throw new EntityNotFoundException("Email: " + userEmail + " no está registrado");
		}
		
		log.info("Usuario: {}, Paráemtro: {}", optUser.get().getUserName(), collectionSet);
		
		
		// 3. Buscar la coleccion del usuario
		Optional<CardUserCollection> optCollectionUser = this.cardUserCollectionRepository.findByUser(optUser.get());
		
		// 3.1 --> Tengo que crear la colección cuando el usuario se loguea por primera vez
		if(optCollectionUser.isEmpty()) {
			throw new EntityNotFoundException("El usuario no tiene una colección inicializada.");
		}
		
		// 4. Buscar por setId
		List<Pokemon> pokemonCollection = this.pokemonRepository.findBySetId(getSetId(collectionSet));
		
		log.info("Tamaño de la expansion de Genetic: {} = 286", pokemonCollection.size());
		
		
		if (pokemonCollection.isEmpty()) {
	        throw new EntityNotFoundException("No hay cartas disponibles para la colección " + collectionSet);
	    }
		
		// 5. Crear las userCards y asignarlas a la colección
		List<UserCards> userCards = pokemonCollection.stream().map(pokemon -> {
			UserCards card = new UserCards();
			card.setCardUserCollection(optCollectionUser.get());
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
		// 1. Buscar usuario por email
		Optional<User> optUser = this.userRepository.findByEmail(userEmail);

		// 2: Verificar que existe un usuario con ese email
		if (!optUser.isPresent()) {
			throw new EntityNotFoundException("Email: " + userEmail + " no está registrado");
		}

		log.info("Usuario: {}, Paráemtro: {}", optUser.get().getUserName(), collectionSet);

		// 3. Buscar la coleccion del usuario
		Optional<CardUserCollection> optCollectionUser = this.cardUserCollectionRepository.findByUser(optUser.get());
		if (optCollectionUser.isEmpty()) {
			throw new EntityNotFoundException("El usuario no tiene una colección inicializada.");
		}
		
		// 4. Recuperar las usercards dependiendo del collectionSet
		List<UserCards> userCardsList = this.userCardsRepository.findByCardUserCollectionAndCategory(optCollectionUser.get(), getCategory(collectionSet));
		if (userCardsList.isEmpty()) {
	        throw new EntityNotFoundException("No hay cartas en la categoría " + collectionSet + " para este usuario.");
	    }
		
		log.info("Se encontraron {} cartas en la expansión {}", userCardsList.size(), collectionSet);
		
		List<PokemonCollectionDTO> pokemonsDTO = userCardsList.stream()
				.map(userCard -> this.pokemonMapper.toPokemonCollectionDTO(userCard.getPokemon())).toList();
				
		return pokemonsDTO;
		
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


