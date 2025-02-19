package com.Backend.specification;

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
        for(Map.Entry<String, Object> entry: filters.entrySet()) {
        	String key = entry.getKey();
        	Object value = entry.getValue();
        	if(value != null && !value.toString().isEmpty()) {
        		
        		// Caso setId
        		if(key.equals("setId")) {
        			predicate = cb.and(predicate, cb.equal(root.get("setId"), value));
        		}
        		
        		// Caso cardType
        		if(key.equals("cardType")) {
        			predicate = cb.and(predicate, cb.equal(root.get("cardType"), value));
        		}
        		
        		// Caso Ordenacion (por defecto: ASC)
        		if(key.equals("sortOrder")) {
        			predicate = cb.and(predicate, cb.equal(root.get("sortOrder"), value));
        		}
        		
        	}
        }

        return predicate;
    }
}
