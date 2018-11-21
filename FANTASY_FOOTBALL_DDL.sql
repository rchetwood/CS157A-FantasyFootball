CREATE DATABASE IF NOT EXISTS myproducts;

USE myproducts;

CREATE TABLE Products (
    maker VARCHAR(255),
    model INT,
    type VARCHAR(255),
    PRIMARY KEY(model)
);

CREATE TABLE PC (
    model INT,
    speed FLOAT,
    ram FLOAT,
    hd FLOAT,
    price INT,
    PRIMARY KEY(model)
);

CREATE TABLE Laptop (
    model INT,
    speed FLOAT,
    ram FLOAT,
    hd FLOAT,
    screen INT,
    price INT,
    PRIMARY KEY(model)
);

CREATE TABLE Printer (
    model INT,
    color BOOLEAN,
    type VARCHAR(255),
    price INT,
    PRIMARY KEY(model)
);