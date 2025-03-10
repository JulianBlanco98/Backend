INSERT INTO card_user_collection (id, user_email)
VALUES
(1, 'test10@test.com'),
(2, 'test1@test.com'),
(3, 'test2@test.com');

INSERT INTO user_cards (id, user_collection_id, cardId, category, hasTheCard)
VALUES
(1, 1, 'A1-004', 'Genetic', 0),
(2, 1, 'A1-007', 'Genetic', 0),
(3, 1, 'A1a-078', 'Mythical', 0),
(4, 1, 'A1a-086', 'Mythical', 0),
(5, 1, 'A2-033', 'Smackdown', 0),
(6, 1, 'PROMO-014', 'PROMO', 0),
(7, 1, 'PROMO-019', 'PROMO', 0),
(8, 1, 'PROMO-029', 'PROMO', 0),
(9, 1, 'PROMO-039', 'PROMO', 0);