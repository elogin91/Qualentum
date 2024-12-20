USE car_registry;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    surname VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

CREATE TABLE brand (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    warranty INTEGER,
    country VARCHAR(100)
);

CREATE TABLE car (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    brand_id INTEGER,
    model VARCHAR(100),
    milleage INTEGER,
    price DOUBLE,
    year INTEGER,
    description VARCHAR(100),
    colour VARCHAR(100),
    fuel_type VARCHAR(100),
    num_doors VARCHAR(100),
    FOREIGN KEY (brand_id) REFERENCES brand(id)
    );

