-- Online Workshop Registration System - Database Setup Script
-- Database: MySQL
-- Run this script in MySQL before starting the application.

DROP DATABASE IF EXISTS workshop_registration_system;

CREATE DATABASE workshop_registration_system;

USE workshop_registration_system;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE workshops (
    workshop_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    capacity INT NOT NULL,
    registered_count INT DEFAULT 0,
    trainer_id INT,
    FOREIGN KEY (trainer_id) REFERENCES users(user_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    workshop_id INT NOT NULL,
    registration_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (workshop_id) REFERENCES workshops(workshop_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    UNIQUE (user_id, workshop_id)
);

-- Default test accounts (password: 1234)
INSERT INTO users (name, email, password, role) VALUES
('Admin User',       'admin@example.com',       '1234', 'Administrator'),
('Trainer User',     'trainer@example.com',     '1234', 'Trainer'),
('Participant User', 'participant@example.com', '1234', 'Participant');

-- Sample workshops (trainer_id = 2, which is "Trainer User")
INSERT INTO workshops (title, description, capacity, registered_count, trainer_id) VALUES
('Java Basics Workshop',  'Introduction to Java programming.',        30, 0, 2),
('JavaFX Workshop',       'Building GUI applications using JavaFX.',  25, 0, 2),
('Database Workshop',     'Introduction to MySQL database concepts.', 20, 0, 2);
