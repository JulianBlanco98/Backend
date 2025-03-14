package com.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backend.dto.DashboardUserCollectionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.service.CardUserCollectionService;
import com.backend.dto.ListCardsUserCardsUpdateDTO;
import com.backend.dto.PokemonCollectionDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pokemonTGC/collection")
public class CardUserCollectionController {
	
	private final CardUserCollectionService cardUserCollectionService;
	
	
	@GetMapping("/{collectionSet}/initialized")
	public ResponseEntity<Map<String, Object>> isCollectionInitialized(@PathVariable final String collectionSet, @AuthenticationPrincipal UserDetails userDetails) {
		
		
		// 1. Recuperar el token para obtener el email del usuario
		String userEmail = userDetails.getUsername();
		log.info("Verificando si la expansión {} está inicializada para el usuario {}", collectionSet, userEmail);
		
		
		// 2. Ver si está inicalizado o no
		boolean isInit = this.cardUserCollectionService.isCollectionInitialized(userEmail, collectionSet);
		
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Expansion " + collectionSet + (isInit ? " ya inicializada" : " no inicializada"));
		response.put("collectionInitialized", isInit);
		
		return ResponseEntity.status(200).body(response);
		
	}
	
	@PostMapping("/{collectionSet}/initialize")
	public ResponseEntity<Map<String, Object>> initColection(@PathVariable final String collectionSet, @AuthenticationPrincipal UserDetails userDetails) {
		// 1. Recuperar el token para obtener el email del usuario
		String userEmail = userDetails.getUsername();
		log.info("Verificando si la expansión {} está inicializada para el usuario {}", collectionSet, userEmail);
	
		// 2. Inicializar la colección según el parámetro
		
		int cardsCount = this.cardUserCollectionService.initializeCollection(userEmail, collectionSet);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "La expansion " + collectionSet + " se ha inicializado correctamente");
		response.put("initialized", true);
		response.put("cardsCount", cardsCount);
		
		return ResponseEntity.status(201).body(response);
	}
	
	@GetMapping("/{collectionSet}/cards")
	public ResponseEntity<Map<String, Object>> getUserExpansion(@PathVariable final String collectionSet, @AuthenticationPrincipal UserDetails userDetails) {
		
		String userEmail = userDetails.getUsername();
		log.info("Verificando si la expansión {} está inicializada para el usuario {}", collectionSet, userEmail);
		
		List<PokemonCollectionDTO> cards = this.cardUserCollectionService.getUserExpansionCards(userEmail, collectionSet);
		Map<String, Object> response = new HashMap<>();
		response.put("cards", cards);
		
		return ResponseEntity.status(200).body(response);
		
	}
	
	@PutMapping("/{collectionSet}")
	public ResponseEntity<Map<String, Object>> updateUserExpansion(@PathVariable final String collectionSet, @AuthenticationPrincipal UserDetails userDetails, @RequestBody ListCardsUserCardsUpdateDTO requestBody) {
		String userEmail = userDetails.getUsername();
		log.info("Verificando si la expansión {} está inicializada para el usuario {}", collectionSet, userEmail);
		int update = this.cardUserCollectionService.updateUserCollection(userEmail, collectionSet, requestBody);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Colección actualizada correctamente "+ update);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}

	@GetMapping("/dashboard")
	public ResponseEntity<Map<String, Object>> getDashboardUserCollection(@AuthenticationPrincipal UserDetails userDetails) {
		String userEmail = userDetails.getUsername();
		log.info("Verificando usuario {}", userEmail);
		DashboardUserCollectionDTO dashboard = this.cardUserCollectionService.getDashboardUserCollection(userEmail);
		Map<String, Object> response = new HashMap<>();
		response.put("message", "Datos recuperados correctamente");
		response.put("data", dashboard);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
