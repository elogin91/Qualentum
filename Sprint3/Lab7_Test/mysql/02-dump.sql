-- Inserción de 5 registros en la tabla brand
INSERT INTO brand (name, warranty, country) VALUES
('Toyota', 5, 'Japan'),
('Ford', 3, 'USA'),
('BMW', 4, 'Germany'),
('Kia', 7, 'South Korea'),
('Honda', 5, 'Japan');

-- Inserción de 5 registros en la tabla car
INSERT INTO car (brand_id, model, milleage, price, year, description, colour, fuel_type, num_doors) VALUES
(1, 'Corolla', 30000, 15000, 2018, 'Compact car', 'White', 'Gasoline', '4'),
(2, 'Mustang', 15000, 35000, 2020, 'Sports car', 'Red', 'Gasoline', '2'),
(3, 'X5', 50000, 45000, 2019, 'Luxury SUV', 'Black', 'Diesel', '5'),
(4, 'Soul', 20000, 17000, 2021, 'Compact SUV', 'Blue', 'Electric', '4'),
(5, 'Civic', 25000, 18000, 2017, 'Sedan', 'Silver', 'Hybrid', '4');

-- Inserción de 2 registros en la tabla user
INSERT INTO users (name, surname, email, password, role)
VALUES
('Juan', 'Pérez', 'juan@example.com', '$2a$10$PaI2BKYz14AJz387zTv5VehP.kNOHGnLp4qq2WtNOqhYknz2ziohO', 'ROLE_CLIENT'),
('Lucía', 'Sánchez', 'lucia@example.com', '$2a$10$PaI2BKYz14AJz387zTv5VehP.kNOHGnLp4qq2WtNOqhYknz2ziohO', 'ROLE_VENDOR');
