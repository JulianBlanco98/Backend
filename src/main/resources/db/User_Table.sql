CREATE TABLE users (
    email VARCHAR(255) NOT NULL unique primary key,
    userName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    nickName VARCHAR(255) NOT NULL,   
    dateOfBirth DATE,
    password VARCHAR(100) NOT NULL,
    rol ENUM('user', 'admin') NOT null default 'user'
) ENGINE=InnoDB;