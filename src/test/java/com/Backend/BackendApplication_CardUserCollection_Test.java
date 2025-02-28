package com.Backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Backend.Service.CardUserCollectionService;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BackendApplication_CardUserCollection_Test {
	
	@Autowired
	private CardUserCollectionService cardUserCollectionService;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void test_isCollectionInitialized_IncorrectGmail() {
		
		assertThrows(EntityNotFoundException.class, () -> {
			this.cardUserCollectionService.isCollectionInitialized("random@random.com", "Genetic");
		});
		
	}
	@Test
	void test_isCollectionInitialized_IncorrectExpansion() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			this.cardUserCollectionService.isCollectionInitialized("prueba@gmail.com", "sdsdsdsd");
		});
		
	}
	@Test
	void test_isCollectionInitialized() {
		
		assertTrue(this.cardUserCollectionService.isCollectionInitialized("prueba@gmail.com", "Genetic"));
	}

}
