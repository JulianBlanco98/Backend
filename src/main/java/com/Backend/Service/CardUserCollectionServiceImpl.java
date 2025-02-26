package com.Backend.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.Pokemon;
import com.Backend.Model.User;
import com.Backend.Repository.CardUserCollectionRepository;
import com.Backend.Repository.PokemonRepository;
import com.Backend.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardUserCollectionServiceImpl implements CardUserCollectionService{

	private final CardUserCollectionRepository cardUserCollectionRepository;
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
		return false;
//		return this.cardUserCollectionDetailRepository.existsByCardUserCollectionAndCollectionType(optUserCollection.get(), collectionSet);
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
		
		
		
		return null;
	}

}
