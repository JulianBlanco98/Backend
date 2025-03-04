package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.CardUserCollection;
import com.backend.model.User;

@Repository
public interface CardUserCollectionRepository extends JpaRepository<CardUserCollection, Long>{

	Optional<CardUserCollection> findByUser(User user);
	
}
