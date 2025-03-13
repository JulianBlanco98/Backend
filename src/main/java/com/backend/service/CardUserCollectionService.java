package com.backend.service;

import java.util.List;

import com.backend.dto.DashboardUserCollectionDTO;
import com.backend.dto.ListCardsUserCardsUpdateDTO;
import com.backend.dto.PokemonCollectionDTO;

public interface CardUserCollectionService {

	public boolean isCollectionInitialized(String userEmail, String collectionSet);
	public int initializeCollection(String userEmail, String collectionSet);
	public List<PokemonCollectionDTO> getUserExpansionCards(String userEmail, String collectionSet);
	public int updateUserCollection(String userEmail, String collectionSet, ListCardsUserCardsUpdateDTO updatedCards);
	public DashboardUserCollectionDTO getDashboardUserCollection(String userEmail);
}
