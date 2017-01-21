
-- INSERT INTO UserAccount (id, username, password) VALUES ('2', 'kelly', 'GV2b25l');
DELETE FROM UserAccount;
DELETE FROM Status;
DELETE FROM Friends;

INSERT INTO UserAccount (id, username, password) VALUES ('3', 'kelly', 'caracas');
INSERT INTO UserAccount (id, username, password) VALUES ('4', 'tero', 'tampere');
INSERT INTO UserAccount (id, username, password) VALUES ('5', 'tuomas', 'oulu');
INSERT INTO UserAccount (id, username, password) VALUES ('6', 'lemmy', 'losangeles');
INSERT INTO UserAccount (id, username, password) VALUES ('7', 'henkka', 'seattle');
INSERT INTO UserAccount (id, username, password) VALUES ('8', 'robert', 'london');
INSERT INTO UserAccount (id, username, password) VALUES ('9', 'jimmy', 'london');
INSERT INTO UserAccount (id, username, password) VALUES ('10', 'john_paul', 'countryside');
INSERT INTO UserAccount (id, username, password) VALUES ('11', 'Bonham', 'heaven');


INSERT INTO Status (id, message, userid) VALUES (1, 'Good day to ride a bike', 'tero');
INSERT INTO Status (id, message, userid) VALUES (2, 'Hace calor', 'kelly');
INSERT INTO Status (id, message, userid) VALUES (3, 'Fines es facil', 'kelly');
INSERT INTO Status (id, message, userid) VALUES (4, 'Nice bikeride', 'tero');
INSERT INTO Status (id, message, userid) VALUES (5, 'Oulun polut parhaita', 'tuomas');
INSERT INTO Status (id, message, userid) VALUES (6, 'tero is simpatico', 'kelly');
INSERT INTO Status (id, message, userid) VALUES (7, 'we play rock and roll', 'lemmy');
INSERT INTO Status (id, message, userid) VALUES (8, 'loudest band on earth', 'lemmy');
INSERT INTO Status (id, message, userid) VALUES (9, 'Foxy lady', 'henkka');
INSERT INTO Status (id, message, userid) VALUES (10, 'stairway to heaven', 'robert');
INSERT INTO Status (id, message, userid) VALUES (11, 'shalalaa', 'robert');
INSERT INTO Status (id, message, userid) VALUES (12, 'black dog', 'jimmmy');
INSERT INTO Status (id, message, userid) VALUES (13, 'Lucifer soundtrack', 'jimmy');
INSERT INTO Status (id, message, userid) VALUES (14, 'john_paul', 'lets go ice fishing');
INSERT INTO Status (id, message, userid) VALUES (15, 'no quarter', 'john_paul');
INSERT INTO Status (id, message, userid) VALUES (16, 'bonham', 'Bonzos  montreux');



INSERT INTO Friends (a_id, b_id) VALUES (3, 4);
INSERT INTO Friends (a_id, b_id) VALUES (4, 3);
INSERT INTO Friends (a_id, b_id) VALUES (4, 5);
