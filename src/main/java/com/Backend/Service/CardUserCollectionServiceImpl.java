package com.Backend.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.Pokemon;
import com.Backend.Model.User;
import com.Backend.Repository.CardUserCollectionDetailRepository;
import com.Backend.Repository.CardUserCollectionRepository;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardUserCollectionServiceImpl implements CardUserCollectionService{

	private final CardUserCollectionRepository cardUserCollectionRepository;
	private final CardUserCollectionDetailRepository cardUserCollectionDetailRepository;
	private final UserRepository userRepository;
	private final PokemonRepository pokemonRepository;
	
	
	
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
		
		return this.cardUserCollectionDetailRepository.existsByCardUserCollectionAndCollectionType(optUserCollection.get(), collectionSet);
	}



	@Override
	public String initializeCollection(String userEmail, String collectionSet) {
		
		// 1. Buscar usuario por email
		Optional<User> optUser = this.userRepository.findByEmail(userEmail);

		// 2: Verificar que existe un usuario con ese email
		if (!optUser.isPresent()) {
			throw new EntityNotFoundException("Email: " + userEmail + " no está registrado");
		}
		
		// 3. Buscar la coleccion del usuario
		CardUserCollection userCollection = cardUserCollectionRepository.findByUser(optUser.get())
                .orElseGet(() -> {
                    CardUserCollection newCollection = new CardUserCollection();
                    newCollection.setUser(optUser.get());
                    newCollection.setCards(new HashSet<>());
                    return cardUserCollectionRepository.save(newCollection);
                });

		boolean isInit = userCollection.getCards().stream()
				.anyMatch(card -> card.getCollectionType().equals(collectionSet));
		
		if(!isInit) {
			return "La expansion " + collectionSet + " ya esta inicializada";
		}
		
		// 4. Recuperar el campo correcto
		String campoCorrecto;
		switch (collectionSet) {
		case "Genetic":
			campoCorrecto = "A1";
			break;
		case "Mythical":
			campoCorrecto = "A1a";
			break;
		case "Smackdown":
			campoCorrecto = "A2";
			break;
		default:
			campoCorrecto = "PROMO";
			break;
		}
		
		// 5. Buscar las cartas de la expansión
		List<Pokemon> expansionCards = this.pokemonRepository.findBySetId(collectionSet);
		
		if (expansionCards.isEmpty()) {
            return "No hay cartas disponibles para la expansión: " + collectionSet;
        }
		
		// 6. Añadirlas a la colección
		
		
		return null;
	}

}
