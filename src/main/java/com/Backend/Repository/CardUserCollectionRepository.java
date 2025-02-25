package com.Backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Backend.Model.CardUserCollection;
import com.Backend.Model.User;

@Repository
public interface CardUserCollectionRepository extends JpaRepository<CardUserCollection, Long>{

	Optional<CardUserCollection> findByUser(User user);
	
}
