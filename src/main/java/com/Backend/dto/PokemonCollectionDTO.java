package com.Backend.dto;

public class PokemonCollectionDTO {

	private String idPokemon;
	private String pokemonName;
	private String imageUrl;
	public String getIdPokemon() {
		return idPokemon;
	}
	public void setIdPokemon(String idPokemon) {
		this.idPokemon = idPokemon;
	}
	public String getPokemonName() {
		return pokemonName;
	}
	public void setPokemonName(String pokemonName) {
		this.pokemonName = pokemonName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "PokemonCollectionDTO [idPokemon=" + idPokemon + ", pokemonName=" + pokemonName + ", imageUrl="
				+ imageUrl + "]";
	}
	
	
}
