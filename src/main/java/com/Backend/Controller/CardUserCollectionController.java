package com.Backend.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Backend.Service.CardUserCollectionService;

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

}
