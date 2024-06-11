-- src/test/resources/test-data.sql

INSERT INTO users (id, username, email, password, security_question, security_answer)
VALUES (DEFAULT, 'user1', 'user1@example.com', 'password1', 'What is your pet''s name?', 'Fluffy');

INSERT INTO users (id, username, email, password, security_question, security_answer)
VALUES (DEFAULT, 'user2', 'user2@example.com', 'password2', 'What is your mother''s maiden name?', 'Smith');

INSERT INTO users (id, username, email, password, security_question, security_answer)
VALUES (DEFAULT, 'user3', 'user3@example.com', 'password3', 'What is your favorite color?', 'Blue');
