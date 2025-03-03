package com.Backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Backend.Model.UserCards;
import com.Backend.Model.CardUserCollection;
import com.Backend.Model.UserCards.CardCategory;
import com.Backend.dto.UserCardsDTO;


public interface UserCardsRepository extends JpaRepository<UserCards, Long>{
	
	boolean existsByCardUserCollectionAndCategory(CardUserCollection cardUserCollection, CardCategory category);
	List<UserCards> findByCardUserCollectionAndCategory(CardUserCollection cardUserCollection, CardCategory category);
	List<UserCards> findByCardUserCollection(CardUserCollection cardUserCollection);
	void deleteByCardUserCollection(CardUserCollection cardUserCollection);

}
