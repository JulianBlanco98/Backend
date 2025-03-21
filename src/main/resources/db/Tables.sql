CREATE TABLE users (
    email VARCHAR(255) NOT NULL unique primary key,
    userName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    nickName VARCHAR(255) NOT NULL,   
    dateOfBirth DATE,
    password VARCHAR(100) NOT NULL,
    rol ENUM('user', 'admin') NOT null default 'user'
) ENGINE=InnoDB;

CREATE TABLE pokemons (
	idPokemon VARCHAR(15) NOT NULL UNIQUE PRIMARY KEY,
	setId VARCHAR(10) NOT NULL,
	idNumber INT UNSIGNED NOT NULL CHECK(idNumber BETWEEN 0 AND 287),
	pokemonName VARCHAR(40) NOT NULL,
	collectionPocket VARCHAR(30) NOT NULL,
	deck VARCHAR(8),
	cardType VARCHAR(15) NOT NULL,
	cardColor VARCHAR(15),
	rarity TINYINT UNSIGNED NOT NULL CHECK(rarity BETWEEN 0 AND 10),
	cardStage VARCHAR(15) NOT NULL,
	retreatCost TINYINT UNSIGNED NOT NULL CHECK(retreatCost BETWEEN 0 AND 4),
	imageUrl VARCHAR(110) NOT NULL,
	hpPokemon TINYINT UNSIGNED NOT NULL CHECK(hpPokemon BETWEEN 0 AND 200),
	textCards VARCHAR(255),
	weakness VARCHAR(15),
	createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE cardsRelated (
	id VARCHAR(15) PRIMARY KEY
) ENGINE=InnoDB;

CREATE TABLE attack (
    id INT AUTO_INCREMENT PRIMARY KEY,
    info VARCHAR(255) NOT NULL,
    effect VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE abilitiy (
    id INT AUTO_INCREMENT PRIMARY KEY,
    info VARCHAR(255) NOT NULL,
    effect VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE card_user_collection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_email) REFERENCES users(email) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE user_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_collection_id BIGINT NOT NULL,
    cardId VARCHAR(15) NOT NULL,
    category ENUM('Genetic', 'PROMO', 'Mythical', 'Smackdown') NOT NULL,
    hasTheCard BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_collection_id) REFERENCES card_user_collection(id) ON DELETE CASCADE,
    FOREIGN KEY (cardId) REFERENCES pokemons(idPokemon) ON DELETE CASCADE
) ENGINE=InnoDB;
