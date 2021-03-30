CREATE SCHEMA IF NOT EXISTS `workshop2`;
USE `workshop2`;

-- ------ TABLE USERS ------------
CREATE TABLE `users` (
    `id`       INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email`    VARCHAR(255) UNIQUE,
    `username` VARCHAR(255),
    `password` VARCHAR(60)
);

-- ------ SAMPLE DATA ------------
INSERT INTO `users` (email, username, password) VALUES
('email1@email.com', 'username1', 'password1'),
('email2@email.com', 'username2', 'password2'),
('email3@email.com', 'username3', 'password3'),
('email4@email.com', 'username4', 'password4'),
('email5@email.com', 'username5', 'password5'),
('email6@email.com', 'username6', 'password6'),
('email7@email.com', 'username7', 'password7'),
('email8@email.com', 'username8', 'password8'),
('email9@email.com', 'username9', 'password9'),
('email10@email.com', 'username10', 'password10'),
('email11@email.com', 'username11', 'password11');