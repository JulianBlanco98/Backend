package com.Backend.dto;

import lombok.Data;

@Data
public class CardUserCollectionDeatilsDTO {
	private Long id;
	private Long collectionId;
	private Long pokemonId;
	private boolean hasTheCard;
	private String collectionType;
	
}
