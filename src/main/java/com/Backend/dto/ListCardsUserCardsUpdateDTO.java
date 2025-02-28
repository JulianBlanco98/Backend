package com.Backend.dto;

import java.util.List;

public class ListCardsUserCardsUpdateDTO {

	private List<UserCardsDTO> cards;

	public List<UserCardsDTO> getCards() {
		return cards;
	}

	public void setCards(List<UserCardsDTO> cards) {
		this.cards = cards;
	}
	
}
