-- Primero, añado los ataques y las habilidades
INSERT INTO attack (id, info, effect)
VALUES
(1, '{GGCC} Mega Drain 80', 'Heal 30 damage from this Pokémon');

INSERT INTO ability (id, effect, info)
VALUES
(1, 'Once during your turn, you may heal 20 damage from each of your Pokémon.', 'Powder Heal');


INSERT INTO pokemons (idPokemon, setId, idNumber, pokemonName, collectionPocket, deck, cardType, cardColor, rarity, cardStage, retreatCost, imageUrl, hpPokemon, textCards, weakness)
VALUES
('A1-004', 'A1', 4, 'Venusaur ex', 'Genetic Apex', 'A1M', 'Pokemon', 'Grass', 5, 'Stage 2', 3, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-004.webp', 190, NULL, 'Fire'),
('A1-007', 'A1', 7, 'Butterfree', 'Genetic Apex', 'A1P', 'Pokemon', 'Grass', 4, 'Stage 2', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-007.webp', 120, NULL, 'Fire');



