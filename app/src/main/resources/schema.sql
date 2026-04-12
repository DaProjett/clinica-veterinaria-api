CREATE TABLE dueno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE mascota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    especie VARCHAR(50),
    raza VARCHAR(50),
    edad INT,
    dueno_id INT,
    FOREIGN KEY (dueno_id) REFERENCES dueno(id)
);

CREATE TABLE cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    motivo VARCHAR(255),
    mascota_id INT,
    FOREIGN KEY (mascota_id) REFERENCES mascota(id)
);
