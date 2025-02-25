package com.Backend.dto;

import lombok.Data;

@Data
public class CardUserCollectionDeatilsDTO {
	private Long id;
	private Long collectionId;
	private String pokemonId;
	private boolean hasTheCard;
	private String collectionType;
	
}
