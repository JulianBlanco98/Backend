package com.Backend.dto;

import com.Backend.Model.UserCards.CardCategory;

import lombok.Data;

@Data
public class UserCardsDTO {
	private Long id;
	private Long user_collection_id;
	private String cardId;
	private CardCategory category;
	private boolean hasTheCard;
	
}
