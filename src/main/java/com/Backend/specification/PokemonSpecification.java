package com.Backend.specification;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;
import com.Backend.Model.Pokemon;
import jakarta.persistence.criteria.*;

public class PokemonSpecification implements Specification<Pokemon> {

    private static final long serialVersionUID = 1L;
    private final Map<String, Object> filters;

    public PokemonSpecification(Map<String, Object> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root<Pokemon> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.conjunction(); // Inicia con AND vacío

        // Recoorer el filtro (mapa)
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null && !value.toString().isEmpty()) {
                switch (key) {
                
                	case "deck": // buscar por deck
                		
                		Map<String, String> deckMap = new HashMap<>();
                		deckMap.put("A1", "A1");
                		deckMap.put("PROMO", "PROMO");
                		deckMap.put("A2", "A2");
                		
                		// deck = A1, PROMO, A2 --> buscar por setId
                		if(deckMap.containsKey(value.toString())) {
                			predicate = cb.and(predicate, cb.equal(root.get("setId"), deckMap.get(value.toString())));
                		}
                		// si es A1M, A1P, A1C, PROMOA1, PROMOA2, PROMOA3, A2D, A2P
                		else {
                	        predicate = cb.and(predicate, cb.like(root.get("deck"), "%" + value.toString() + "%"));
                		}
                		break;
                    case "pokemonName": //buscar por pokemonName
                    	predicate = cb.and(predicate, cb.like(cb.lower(root.get("pokemonName")), "%" + value.toString().toLowerCase() + "%"));
                        break;
                    case "cardType": //buscar por cardType
                        predicate = cb.and(predicate, cb.equal(root.get("cardType"), value));
                        break;
                    case "cardColor": //buscar por cardColor
                    	predicate = cb.and(predicate, cb.equal(root.get("cardColor"), value));
                    	break;
                    case "rarity": //buscar por rarity (string a number)
                    	predicate = cb.and(predicate, cb.equal(root.get("rarity"), Integer.parseInt(value.toString())));
                    	break;
                    case "cardStage": //buscar por cardStage
                    	predicate = cb.and(predicate, cb.equal(root.get("cardStage"), value));
                    	break;
                    case "weakness": //buscar por weakness
                    	predicate = cb.and(predicate, cb.equal(root.get("weakness"), value));
                    	break;
                    case "retreatCost": //buscar por retreatCost (string a number)
                    	predicate = cb.and(predicate, cb.equal(root.get("retreatCost"), Integer.parseInt(value.toString())));
                    	break;
                    case "sortBy":
                    	if(value.toString().equals("pokemonName")) {
                    		query.orderBy(cb.asc(root.get("pokemonName")));
                    	}
                    	else if(value.toString().equals("cardColor")) {
                    		query.orderBy(cb.asc(root.get("cardColor")));                    		
                    	}
                    	else if(value.toString().equals("setId")) {
                    		query.orderBy(cb.asc(root.get("setId")));                    		
                    	}
                    	else {
                    		return cb.disjunction(); 
                    	}
                    	break;
                    	
                    case "sortOrder": // Aplicar ordenación
                        String sortOrder = value.toString().toUpperCase();
                        if (sortOrder.equals("ASC")) {
                            query.orderBy(cb.asc(root.get("setId"))); // Ordena por setId
                        } 
                        else {
                            query.orderBy(cb.desc(root.get("setId")));
                        }
                        break;
                }
            }
        }

        return predicate;
    }
}
