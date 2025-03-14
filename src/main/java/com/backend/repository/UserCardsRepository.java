package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.UserCards;
import com.backend.model.CardUserCollection;
import com.backend.model.UserCards.CardCategory;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardsRepository extends JpaRepository<UserCards, Long>{
	
	boolean existsByCardUserCollectionAndCategory(CardUserCollection cardUserCollection, CardCategory category);
	List<UserCards> findByCardUserCollectionAndCategory(CardUserCollection cardUserCollection, CardCategory category);
	List<UserCards> findByCardUserCollection(CardUserCollection cardUserCollection);
	void deleteByCardUserCollection(CardUserCollection cardUserCollection);
	Long countByCategoryAndHasTheCardTrue(CardCategory category);
}
