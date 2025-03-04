package com.backend.dto;

import com.backend.model.UserCards.CardCategory;

import lombok.Data;

@Data
public class UserCardsDTO {
	private Long id;
	private Long user_collection_id;
	private String cardId;
	private CardCategory category;
	private boolean hasTheCard;
	
}
