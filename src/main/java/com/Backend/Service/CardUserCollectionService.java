package com.Backend.Service;

public interface CardUserCollectionService {

	public boolean isCollectionInitialized(String userEmail, String collectionSet);
	public String initializeCollection(String userEmail, String collectionSet);
	
}
