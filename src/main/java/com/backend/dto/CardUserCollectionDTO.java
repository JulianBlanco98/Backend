package com.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class CardUserCollectionDTO {

	private Long id;
	private String userEmail;
	private List<UserCardsDTO> cards;
	
}
