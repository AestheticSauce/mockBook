-- src/test/resources/test-data.sql

INSERT INTO users (id, username, email, password, security_question, security_answer, enabled, role)
VALUES (DEFAULT, 'user1', 'user1@example.com', '$2a$12$6GihCil8PIw0ukCCWwT9NOL05d97/wmQ9eysZas3IyLj47IqFsatO', 'What is your pet''s name?', 'Fluffy', 1, 'USER');

INSERT INTO users (id, username, email, password, security_question, security_answer, enabled, role)
VALUES (DEFAULT, 'user2', 'user2@example.com', '$2a$12$hE7dcvvXGRUm94z3wCkW1ec5ZHwjhCQ824sc0hVS0.AR3v.t40XJO', 'What is your mother''s maiden name?', 'Smith', 1, 'USER');

INSERT INTO users (id, username, email, password, security_question, security_answer, enabled, role)
VALUES (DEFAULT, 'user3', 'user3@example.com', '$2a$12$Z8voFdWoDNeRpRVQvP4EvuRVrM7bIBqSIT3N9b/pdma3j3oUwKQSW', 'What is your favorite color?', 'Blue', 1, 'USER');

INSERT INTO users (id, username, email, password, security_question, security_answer, enabled, role)
VALUES (DEFAULT, 'user4', 'user4@example.com', '$2a$12$Z8voFdWoDNeRpRVQvP4EvuRVrM7bIBqSIT3N9b/pdma3j3oUwKQSW', 'Question?', 'Another one', 1, 'USER');

-- Friend request from user1 to user2
INSERT INTO friendship (id, source_user_id, target_user_id, status)
VALUES (DEFAULT, 1, 2, 0);

-- Friend request from user2 to user3
INSERT INTO friendship (id, source_user_id, target_user_id, status)
VALUES (DEFAULT, 2, 3, 0);

-- Friend request from user3 to user1, already accepted for testing accept/reject functionality
INSERT INTO friendship (id, source_user_id, target_user_id, status)
VALUES (DEFAULT, 3, 1, 1);

-- Friend request from user1 to user4, already accepted for testing bi-directional relation retrieval
INSERT INTO friendship (id, source_user_id, target_user_id, status)
VALUES (DEFAULT, 1, 4, 1);

-- Post from user1
INSERT INTO post (id, author_id, content, title)
VALUES (DEFAULT, 1, 'Hello, world!', 'First post');
