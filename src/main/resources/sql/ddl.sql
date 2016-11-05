--CREATE DATABASE videogame;
--CREATE USER 'frangoro'@'localhost' IDENTIFIED BY 'frangoro';
--GRANT ALL PRIVILEGES ON videogame.* TO 'frangoro'@'localhost';
--FLUSH PRIVILEGES;
--USE videogame;

--DROP TABLE rental;
--DROP TABLE game;
--DROP TABLE user;

CREATE TABLE user ( -- Video game users
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  points INT, -- Loyality points
  name VARCHAR(50)
);

CREATE TABLE game (-- Games stock for rent
  id INT UNSIGNED NOT NULL AUTO_INCREMENT, 
  PRIMARY KEY (id),
  type VARCHAR(20) NOT NULL CHECK(type IN ('NEW_RELEASE', 'STANDARD', 'CLASSIC')),
  name VARCHAR(50)
);

CREATE TABLE rental ( -- Video game rentals
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id),
  user_id INT UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id),
  game_id INT UNSIGNED NOT NULL,
  FOREIGN KEY (game_id) REFERENCES game(id),
  price INT NOT NULL, -- Rental price regards game.type and days
  surcharge INT, -- Rental surchage regards game.type and extra_days
  days INT NOT NULL, -- How many days do it rented
  extra_days INT, -- How many days do it rented over the amount of days
  rent_date DATE NOT NULL, -- When was it rented
  return_date DATE -- When was it returned
);