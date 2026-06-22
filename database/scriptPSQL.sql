-- Crear tablas
CREATE TABLE address (
    idAddress  SERIAL PRIMARY KEY,
    street     VARCHAR(100),
    number     VARCHAR(10),
    city       VARCHAR(50),
    postalCode VARCHAR(10)
);

CREATE TABLE users (
    idUsuario      SERIAL PRIMARY KEY,
    firstname      VARCHAR(50)  NOT NULL,
    lastname       VARCHAR(50)  NOT NULL,
    email          VARCHAR(100) NOT NULL UNIQUE,
    phonenumber    VARCHAR(20),
    role           VARCHAR(10)  CHECK (role IN ('Admin', 'User')),
    status         VARCHAR(10)  CHECK (status IN ('Active', 'Inactive')),
    password       VARCHAR(255) NOT NULL,
    profilepicture VARCHAR(255),
    idaddress      INT REFERENCES address(idAddress) ON DELETE SET NULL
);