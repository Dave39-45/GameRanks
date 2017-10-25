INSERT INTO USERS (ID, VERSION, USERNAME, EMAIL, PASSWORD, ACCESS_LEVEL) VALUES (0, 0, 'Dave', 'dave@email.com', 'Dave', 'ADMIN');
INSERT INTO USERS (ID, VERSION, USERNAME, EMAIL, PASSWORD, ACCESS_LEVEL) VALUES (1, 0, 'User1', 'user1@email.com', 'User1', 'USER');
INSERT INTO USERS (ID, VERSION, USERNAME, EMAIL, PASSWORD, ACCESS_LEVEL) VALUES (2, 0, 'test', 'test@email.com', 'test', 'USER');



INSERT INTO PUBLISHERS (ID, VERSION, NAME) VALUES (0, 0, 'CD Projekt');



INSERT INTO DEVELOPERS (ID, VERSION, NAME) VALUES (0, 0, 'CD Projekt RED');



INSERT INTO GAMES (ID, VERSION, NAME, PUBLISHER_ID, DEVELOPER_ID, RELEASE_DATE, GENRE) VALUES (0, 0, 'The Witcher 3: Wild Hunt', 0, 0, parsedatetime('19-05-2015', 'dd-MM-yyyy'), 'ACTION#ADVENTURE#RPG');



INSERT INTO REVIEWS (ID, VERSION, GAME_ID, USER_ID, SCORE, PROS, CONS, REVIEW_TEXT, CREATED_ON) VALUES (0, 0, 0, 0, 9, 'One#Two#Three', 'Three#Two#One', 'Review text should be here...', CURRENT_TIMESTAMP());