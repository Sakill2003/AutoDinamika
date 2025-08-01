-- ===============================
-- Drop all tables to avoid errors.
-- ===============================

DROP TABLE IF EXISTS Sales;
DROP TABLE IF EXISTS Car;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS CarModel;
DROP TABLE IF EXISTS Seller;
DROP TABLE IF EXISTS Role;

-- ===============================
-- Table: Role
-- ===============================
CREATE TABLE Role (
    Role_ID SERIAL PRIMARY KEY,
    Role_Name VARCHAR(50) NOT NULL UNIQUE
);

-- ===============================
-- Table: Seller
-- ===============================
CREATE TABLE Seller (
    Seller_ID SERIAL PRIMARY KEY,
    First_Name VARCHAR(100) NOT NULL,
    Last_Name VARCHAR(100) NOT NULL,
    Birthday_Date DATE,
    Phone VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    Address VARCHAR(100),
    Commissions DECIMAL(10, 2) DEFAULT 0.00,
    Password VARCHAR(255) NOT NULL,
    Role_ID INT REFERENCES Role(Role_ID)
);

-- ===============================
-- Table: CarModel
-- ===============================
CREATE TABLE CarModel (
    Model_ID SERIAL PRIMARY KEY,
    Brand VARCHAR(100) NOT NULL,
    Model VARCHAR(100) NOT NULL,
    Year INT CHECK (Year >= 1980),
    Color VARCHAR(50),
    Price DECIMAL(10, 2) NOT NULL,
    IMGF VARCHAR(255) NOT NULL,
    IMGS VARCHAR(255) NOT NULL,
    IMGT VARCHAR(255) NOT NULL
);

-- ===============================
-- Table: Car
-- ===============================
CREATE TABLE Car (
    Chasis VARCHAR(100) PRIMARY KEY,
    Motor VARCHAR(100) NOT NULL UNIQUE,
    Status VARCHAR(20) DEFAULT 'available' CHECK (Status IN ('available', 'sold')),
    Model_ID INT REFERENCES CarModel(Model_ID)
);

-- ===============================
-- Table: Customer
-- ===============================
CREATE TABLE Customer (
    Customer_ID SERIAL PRIMARY KEY,
    Name VARCHAR(150) NOT NULL,
    ID_Type VARCHAR(50) NOT NULL,
    Document_Number VARCHAR(100) NOT NULL UNIQUE
);

-- ===============================
-- Table: Sales
-- ===============================
CREATE TABLE Sales (
    Sale_ID SERIAL PRIMARY KEY,
    Seller_ID INT REFERENCES Seller(Seller_ID),
    Chasis VARCHAR(100) REFERENCES Car(Chasis),
    Customer_ID INT REFERENCES Customer(Customer_ID),
    Payment VARCHAR(100),
    Date DATE DEFAULT CURRENT_DATE
);

-- ===============================
-- INITIAL INSERTS
-- ===============================

-- Roles
INSERT INTO Role (Role_Name) VALUES 
('guest'),
('seller'),
('admin');

-- Sellers
INSERT INTO Seller (First_Name, Last_Name, Birthday_Date, Phone, Email, Address, Password, Role_ID)
VALUES
('Carlos', 'Ramírez', '1990-05-12', '3011234567', 'carlos@email.com', 'Calle 123', 'pass123', 2),
('Laura', 'Gómez', '1988-09-23', '3027654321', 'laura@email.com', 'Carrera 45', 'pass456', 3);

-- Car Models
INSERT INTO CarModel (Brand, Model, Year, Color, Price, IMGF, IMGS, IMGT)
VALUES
('BMW', 'M4 Competition', 2023, 'Midnight Black', 75900.00, 'https://shorturl.at/rNsAX', 'https://shorturl.at/jAHS4', 'https://shorturl.at/cS2QS'),
('Mercedes', 'AMG GT 63 S', 2023, 'Alpine White', 89500.00, 'https://tinyurl.com/3rcwnbk5', 'https://tinyurl.com/42urrmf9', 'https://tinyurl.com/53d2dabt'),
('Audi', 'RS7 Sportback', 2024, 'Nardo Gray', 92800.00, 'https://tinyurl.com/yksv6zsh', 'https://tinyurl.com/4ft9wvcv', 'https://tinyurl.com/mr25wcr6'),
('Porsche', '911 Turbo S', 2025, 'Guards Red', 207000.00, 'https://tinyurl.com/4ne6znmf', 'https://tinyurl.com/bde2t2tx', 'https://tinyurl.com/bp53jex8'),
('Lamborghini', 'Huracan EVO', 2025, 'Arancio Borealis', 248295.00, 'https://tinyurl.com/5dum5nk3', 'https://tinyurl.com/m8k3275x', 'https://tinyurl.com/37she7xe');

-- Cars (Inventory)
INSERT INTO Car (Chasis, Motor, Status, Model_ID)
VALUES
('CHS123ABC', 'MTR123XYZ', 'available', 1),
('CHS456DEF', 'MTR456UVW', 'available', 2),
('CHS7J9KLM', 'MTR8Z4QWE', 'available', 3),
('CHS3X2BDH', 'MTR9L0JUI', 'available', 1),
('CHS5N8RTY', 'MTR7K6OPA', 'available', 5),
('CHS2M1ZXC', 'MTR6V7ASD', 'available', 2),
('CHS4G5PLK', 'MTR5Q1WER', 'available', 4),
('CHS6B3UIO', 'MTR4E9RTY', 'available', 2),
('CHS1A7MNB', 'MTR3T6QAZ', 'available', 5),
('CHS8V0LKJ', 'MTR2Y3WSX', 'available', 3),
('CHS9H4DFG', 'MTR1U5EDC', 'available', 1),
('CHS0C6QWE', 'MTR0I8RFV', 'available', 4),
('CHS789GHI', 'MTR789LMN', 'sold', 1); -- already sold

-- Clients
INSERT INTO Customer (Name, ID_Type, Document_Number)
VALUES
('Juan Pérez', 'CC', '1020304050'),
('Ana Torres', 'CE', '5060708090');

-- Sales
INSERT INTO Sales (Seller_ID, Chasis, Customer_ID, Payment, Date)
VALUES
(1, 'CHS789GHI', 1, 'MasterCard', '2025-07-28');
