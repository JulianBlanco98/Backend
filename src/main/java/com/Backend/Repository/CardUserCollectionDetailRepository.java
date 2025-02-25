package com.Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.CardUserCollectionDetail;

@Repository
public interface CardUserCollectionDetailRepository extends JpaRepository<CardUserCollectionDetail, Long>{

	boolean existsByCardUserCollectionAndCollectionType(CardUserCollection collection, String collectionType);
	
}
