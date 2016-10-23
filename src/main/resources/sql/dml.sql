-- DML Scripts

INSERT INTO user (id, points, name) VALUES (1, 0, "Pepe");
INSERT INTO user (id, points, name) VALUES (2, 0, "Marta");
INSERT INTO user (id, points, name) VALUES (3, 0, "Luis");

INSERT INTO game (id, type, name) VALUES (1, "NEW_RELEASE", "No Man's Sky");
INSERT INTO game (id, type, name) VALUES (2, "STANDARD", "Resident Evil 6");
INSERT INTO game (id, type, name) VALUES (3, "STANDARD", "Fallout 5");
INSERT INTO game (id, type, name) VALUES (4, "CLASSIC", "Fallout 3");

--INSERT INTO game (id, user_id, game_id, price, surcharge, days, extra_days, rent_date, return_date) VALUES (1, 1, 1, 1, 1, 1, 1, '2016-10-23', '2016-10-23');

COMMIT;
