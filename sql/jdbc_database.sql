CREATE DATABASE jdbc_schema;
USE jdbc_schema;
CREATE TABLE animal(
	id BIGINT NOT NULL,
    name VARCHAR(255),
    age INT,
    PRIMARY KEY(id)
);
INSERT INTO animal VALUES
	(1, 'Reksio', 5),
    (2, 'Mruczek', 2),
    (3, 'Bemio', 10),
    (4, 'Filemon', 6);
SELECT * FROM animal;
SELECT * FROM user WHERE username='user1' AND password='password1';
SELECT * FROM user;
SELECT * FROM task;
DESC user;

TRUNCATE TABLE user;
SELECT * FROM user 
	JOIN task ON user.id = task.user_id
    WHERE user.username='user1';
UPDATE task SET 'description'='Read paper' AND 'user_id'=1 WHERE 'id'=6;