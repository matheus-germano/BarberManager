DROP DATABASE IF EXISTS BarberManager;
CREATE DATABASE BarberManager;
USE BarberManager;

CREATE TABLE professional (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    document VARCHAR(11) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN,
    created_at DATE NOT NULL
);

CREATE TABLE payment_method (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR(50) NOT NULL UNIQUE,
    is_active BOOLEAN NOT NULL,
    created_at DATE NOT NULL
);

CREATE TABLE service (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    name VARCHAR (100) NOT NULL,
    price DOUBLE NOT NULL,
    is_active BOOLEAN NOT NULL,
    created_at DATE NOT NULL
);

CREATE TABLE sale (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    professional_document VARCHAR(11) NOT NULL,
    payment_method_id VARCHAR(50) NOT NULL,
    total DOUBLE NOT NULL,
    sold_at DATE NOT NULL,
    FOREIGN KEY (professional_document) REFERENCES professional (document),
    FOREIGN KEY (payment_method_id) REFERENCES payment_method (id)
    ON DELETE CASCADE
);

CREATE TABLE sale_service (
    sale_id CHAR(36),
    service_id CHAR(36),
    PRIMARY KEY (sale_id, service_id),
    FOREIGN KEY (sale_id) REFERENCES sale (id),
    FOREIGN KEY (service_id) REFERENCES service (id)
    ON DELETE CASCADE
);