package com.Backend.Service;

import java.util.List;

import com.Backend.dto.PokemonCollectionDTO;

public interface CardUserCollectionService {

	public boolean isCollectionInitialized(String userEmail, String collectionSet);
	public String initializeCollection(String userEmail, String collectionSet);
	public List<PokemonCollectionDTO> getUserExpansionCards(String userEmail, String collectionSet);
	
}
