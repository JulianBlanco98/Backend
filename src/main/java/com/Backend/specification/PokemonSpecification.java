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
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null && !value.toString().isEmpty()) {
                switch (key) {
                    case "pokemonName": //buscar por pokemonName
                        predicate = cb.and(predicate, cb.equal(root.get("pokemonName"), value));
                        break;
                    case "cardType": //buscar por cardType
                        predicate = cb.and(predicate, cb.equal(root.get("cardType"), value));
                        break;
                    case "cardColor": //buscar por cardColor
                    	predicate = cb.and(predicate, cb.equal(root.get("cardColor"), value));
                    	break;
                    case "rarity": //buscar por rarity (ocject a number)
                    	predicate = cb.and(predicate, cb.equal(root.get("rarity"), Integer.parseInt(value.toString())));
                    	break;
                    case "sortOrder": // Aplicar ordenación
                        String sortOrder = value.toString().toUpperCase();
                        if ("ASC".equals(sortOrder)) {
                            query.orderBy(cb.asc(root.get("setId"))); // Ordena por setId
                        } else {
                            query.orderBy(cb.desc(root.get("setId")));
                        }
                        break;
                }
            }
        }

        return predicate;
    }
}
