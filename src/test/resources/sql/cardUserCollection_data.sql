INSERT INTO card_user_collection (id, user_email)
VALUES
(1, 'test10@test.com'),
(2, 'test1@test.com');

INSERT INTO user_cards (id, user_collection_id, cardId, category, hasTheCard)
VALUES
(1, 1, 'A1-004', 'Genetic', 0),
(2, 1, 'A1-007', 'Genetic', 0),
(3, 1, 'A1a-078', 'Mythical', 0),
(4, 1, 'A1a-086', 'Mythical', 0),
(5, 2, 'A1-004', 'Genetic', 0),
(6, 2, 'A1-007', 'Genetic', 0);