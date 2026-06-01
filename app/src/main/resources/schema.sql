CREATE DATABASE IF NOT EXISTS veterinaria_db;
USE veterinaria_db;

CREATE TABLE IF NOT EXISTS duenos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    direccion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS mascotas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raza VARCHAR(100),
    edad INT NOT NULL,
    id_dueno INT NOT NULL,
    CONSTRAINT fk_mascota_dueno
        FOREIGN KEY (id_dueno)
        REFERENCES duenos(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    id_mascota INT NOT NULL,
    CONSTRAINT fk_cita_mascota
        FOREIGN KEY (id_mascota)
        REFERENCES mascotas(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
