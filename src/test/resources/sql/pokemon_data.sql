-- Primero, añado los ataques
INSERT INTO attack (id, info, effect)
VALUES
(1, '{GCC} Razor Leaf 60', NULL), -- Venusaur Ex
(2, '{GGCC} Mega Drain 80', 'Heal 30 damage from this Pokémon'), -- Venusaur Ex
(3, '{GCC} Gust 60', NULL), -- Butterfree
(4, '{FC} Land Crush 80', NULL), -- Aerodactyl Ex
(5, '{CCC} Genome Hacking', 'Choose 1 of your opponents Active Pokémons attacks and use it as this attack.'); -- Mew Ex

-- Luego, las habilidades

INSERT INTO ability (id, effect, info)
VALUES
(1, 'Once during your turn, you may heal 20 damage from each of your Pokémon.', 'Powder Heal'),
(2, 'Your opponent cant play any Pokémon from their hand to evolve their Active Pokémon.', 'Primeral Law');

-- Luego, añado los pokemon

INSERT INTO pokemons (idPokemon, setId, idNumber, pokemonName, collectionPocket, deck, cardType, cardColor, rarity, cardStage, retreatCost, imageUrl, hpPokemon, textCards, weakness)
VALUES
('A1-004', 'A1', 4, 'Venusaur ex', 'Genetic Apex', 'A1M', 'Pokemon', 'Grass', 5, 'Stage 2', 3, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-004.webp', 190, NULL, 'Fire'),
('A1-007', 'A1', 7, 'Butterfree', 'Genetic Apex', 'A1P', 'Pokemon', 'Grass', 4, 'Stage 2', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-007.webp', 120, NULL, 'Fire'),
('A1a-078', 'A1a', 78, 'Aerodactyl ex', 'Mythical Island', 'Promo', 'Pokemon', 'Fighting', 7, 'Stage 1', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Island/A1a-078.webp', 140, NULL, 'Lightning'),
('A1a-086', 'A1a', 86, 'Mew ex', 'Mythical Island', 'Promo', 'Pokemon', 'Psychic', 10, 'Basic', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Island/A1a-086.webp', 130, NULL, 'Darkness');

-- y por último, relleno ambas tablas intermedias

INSERT INTO pokemon_attack (pokemon_id, attack_id)
VALUES
('A1-004', 1), -- Venusaur Ex
('A1-004', 2), -- Venusaur Ex
('A1-007', 3), -- Butterfree
('A1a-078', 4), -- Aerodactyl Ex
('A1a-086', 5); -- Mew Ex

INSERT INTO pokemon_ability (pokemon_id, ability_id)
VALUES
('A1-007', 1), -- Butterfree
('A1a-078', 2); -- Aerodactyl Ex


