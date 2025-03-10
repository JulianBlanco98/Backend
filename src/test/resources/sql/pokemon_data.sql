-- Primero, añado los ataques
INSERT INTO attack (id, info, effect)
VALUES
(1, '{GCC} Razor Leaf 60', NULL), -- Venusaur Ex
(2, '{GGCC} Mega Drain 80', 'Heal 30 damage from this Pokémon'), -- Venusaur Ex
(3, '{GCC} Gust 60', NULL), -- Butterfree
(4, '{FC} Land Crush 80', NULL), -- Aerodactyl Ex
(5, '{CCC} Genome Hacking', 'Choose 1 of your opponents Active Pokémons attacks and use it as this attack.'), -- Mew Ex
(6, '{WWCC} Frosty Flattening 120', NULL), -- Mamoswine
(7, '{WWC} Bubble Drain 80', 'Heal 20 damage from this Pokémon.'), -- Lapras ex
(8, '{WC} Mist Slash 60', NULL), -- Greninja
(9, '{WWC} Hydro Pump 80+', 'If this Pokémon has at least 2 extra {W} Energy attached, this attack does 60 more damage.'), -- Blastoise
(10, '{M} Metal Arms 20+', 'If this Pokémon has a Pokémon Tool attached, this attack does 30 more damage.'); -- Skarmory


-- Luego, las habilidades

INSERT INTO ability (id, effect, info)
VALUES
(1, 'Once during your turn, you may heal 20 damage from each of your Pokémon.', 'Powder Heal'), -- Butterfree
(2, 'Your opponent cant play any Pokémon from their hand to evolve their Active Pokémon.', 'Primeral Law'), -- Aerodactyl Ex
(3, 'This Pokémon takes −30 damage from attacks from {R} or {W} Pokémon.', 'Thick Fat'), -- Mamoswine
(4, 'Once during your turn, you may do 20 damage to 1 of your opponents Pokémon.', 'Water Shuriken'); -- Greninja


-- Luego, añado los pokemon

INSERT INTO pokemons (idPokemon, setId, idNumber, pokemonName, collectionPocket, deck, cardType, cardColor, rarity, cardStage, retreatCost, imageUrl, hpPokemon, textCards, weakness)
VALUES
('A1-004', 'A1', 4, 'Venusaur ex', 'Genetic Apex', 'A1M', 'Pokemon', 'Grass', 5, 'Stage 2', 3, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-004.webp', 190, NULL, 'Fire'),
('A1-007', 'A1', 7, 'Butterfree', 'Genetic Apex', 'A1P', 'Pokemon', 'Grass', 4, 'Stage 2', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Genetic/A1-007.webp', 120, NULL, 'Fire'),
('A1a-078', 'A1a', 78, 'Aerodactyl ex', 'Mythical Island', 'Promo', 'Pokemon', 'Fighting', 7, 'Stage 1', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Island/A1a-078.webp', 140, NULL, 'Lightning'),
('A1a-086', 'A1a', 86, 'Mew ex', 'Mythical Island', 'Promo', 'Pokemon', 'Psychic', 10, 'Basic', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Island/A1a-086.webp', 130, NULL, 'Darkness'),
('A2-033', 'A2', 33, 'Mamoswine', 'Space-Time Smackdown', 'A2D', 'Pokemon', 'Water', 4, 'Stage 2', 4, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/SmackDown/A2-033.webp', 160, 'This Pokémon can be spotted in wall paintings<br />from as far back as 10,000 years ago. For a<br />while, it was thought to have gone extinct.', 'Metal'),
('PROMO-014', 'PROMO', 14, 'Lapras ex', 'Promo-A', 'PROMOA1', 'Pokemon', 'Water', 1, 'Basic', 3, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Promo/PROMO-014.webp', 140, NULL, 'Lightning'),
('PROMO-019', 'PROMO', 19, 'Greninja', 'Promo-A', 'PROMOA2', 'Pokemon', 'Water', 1, 'Stage 2', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Promo/PROMO-019.webp', 120, NULL, 'Lightning'),
('PROMO-029', 'PROMO', 29, 'Blastoise', 'Promo', 'PROMOA3', 'Pokemon', 'Water', 1, 'Stage 2', 3, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Promo/PROMO-029.webp', 150, NULL, 'Lightning'),
('PROMO-039', 'PROMO', 39, 'Skarmory', 'Promo', 'PROMOA4', 'Pokemon', 'Metal', 1, 'Basic', 1, 'https://res.cloudinary.com/tgcpokemon/image/upload/v1736255346/Promo/PROMO-039.webp', 80, NULL, 'Lightning');


-- y por último, relleno ambas tablas intermedias

INSERT INTO pokemon_attack (pokemon_id, attack_id)
VALUES
('A1-004', 1), -- Venusaur Ex
('A1-004', 2), -- Venusaur Ex
('A1-007', 3), -- Butterfree
('A1a-078', 4), -- Aerodactyl Ex
('A1a-086', 5), -- Mew Ex
('A2-033', 6), -- Mamoswine
('PROMO-014', 7), -- Lapras Ex
('PROMO-019', 8), -- Greninja
('PROMO-029', 9), -- Blastoise
('PROMO-039', 10); -- Skarmory

INSERT INTO pokemon_ability (pokemon_id, ability_id)
VALUES
('A1-007', 1), -- Butterfree
('A1a-078', 2), -- Aerodactyl Ex
('A2-033', 3), -- Mamoswine
('PROMO-019', 3); -- Greninja


