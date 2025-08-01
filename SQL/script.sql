-- Create Database (optional)
CREATE DATABASE IF NOT EXISTS AutoDinamika;
USE AutoDinamika;

-- ===============================
-- Table: Role
-- ===============================
CREATE TABLE Role (
    Role_ID INT PRIMARY KEY AUTO_INCREMENT,
    Role_Name VARCHAR(50) NOT NULL UNIQUE
);

-- ===============================
-- Table: User
-- ===============================
CREATE TABLE User (
    User_ID INT PRIMARY KEY AUTO_INCREMENT,
    First_Name VARCHAR(100) NOT NULL,
    Last_Name VARCHAR(100) NOT NULL,
    Birthday_Date DATE,
    Phone VARCHAR(20),
    Email VARCHAR(100) UNIQUE,
    Address VARCHAR(100),
    Commissions DECIMAL(10, 2) DEFAULT 0.00,
    Password VARCHAR(255) NOT NULL,
    Role_ID INT,
    FOREIGN KEY (Role_ID) REFERENCES Role(Role_ID)
);

-- ===============================
-- Table: CarModel
-- ===============================
CREATE TABLE CarModel (
    Model_ID INT PRIMARY KEY AUTO_INCREMENT,
    Brand VARCHAR(100) NOT NULL,
    Model VARCHAR(100) NOT NULL,
    Year YEAR NOT NULL,
    Color VARCHAR(50),
    Price DECIMAL(10, 2) NOT NULL
);

-- ===============================
-- Table: Car
-- ===============================
CREATE TABLE Car (
    Chasis VARCHAR(100) PRIMARY KEY,
    Motor VARCHAR(100) NOT NULL UNIQUE,
    Status ENUM('available', 'sold') DEFAULT 'available',
    Model_ID INT,
    FOREIGN KEY (Model_ID) REFERENCES CarModel(Model_ID)
);

-- ===============================
-- Table: Customer
-- ===============================
CREATE TABLE Customer (
    Customer_ID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(150) NOT NULL,
    ID_Type VARCHAR(50) NOT NULL,
    Document_Number VARCHAR(100) NOT NULL UNIQUE
);

-- ===============================
-- Table: Sales
-- ===============================
CREATE TABLE Sales (
    Sale_ID INT PRIMARY KEY AUTO_INCREMENT,
    User_ID INT,
    Chasis VARCHAR(100),
    Customer_ID INT,
    Payment VARCHAR(100),
    Date DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (User_ID) REFERENCES User(User_ID),
    FOREIGN KEY (Chasis) REFERENCES Car(Chasis),
    FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID)
);

-- ===============================
-- INITIAL INSERTS
-- ===============================

-- Roles
INSERT INTO Role (Role_Name) VALUES 
('guest'),
('seller'),
('admin');

-- Users
INSERT INTO User (First_Name, Last_Name, Birthday_Date, Phone, Email, Address, Password, Role_ID)
VALUES
('Carlos', 'Ramírez', '1990-05-12', '3011234567', 'carlos@email.com', 'Calle 123', 'pass123', 2),
('Laura', 'Gómez', '1988-09-23', '3027654321', 'laura@email.com', 'Carrera 45', 'pass456', 3);

-- Car Models
INSERT INTO CarModel (Brand, Model, Year, Color, Price)
VALUES
('Toyota', 'Corolla', 2022, 'Rojo', 85000.00),
('Mazda', '3', 2023, 'Negro', 90000.00);

-- Cars (Inventory)
INSERT INTO Car (Chasis, Motor, Status, Model_ID)
VALUES
('CHS123ABC', 'MTR123XYZ', 'available', 1),
('CHS456DEF', 'MTR456UVW', 'available', 2),
('CHS789GHI', 'MTR789LMN', 'sold', 1); -- already sold

-- Clients
INSERT INTO Customer (Name, ID_Type, Document_Number)
VALUES
('Juan Pérez', 'CC', '1020304050'),
('Ana Torres', 'CE', '5060708090');

-- Sales
INSERT INTO Sales (User_ID, Chasis, Customer_ID, Payment, Date)
VALUES
(1, 'CHS789GHI', 1, 'tarjeta de crédito', '2025-07-28');
