package com.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.UserCards;

@Repository
public interface CardUserCollectionDetailRepository extends JpaRepository<UserCards, Long>{

	boolean existsByCardUserCollectionAndCollectionType(CardUserCollection collection, String collectionType);
	
}
