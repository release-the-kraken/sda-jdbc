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
DESC user;

TRUNCATE TABLE user;